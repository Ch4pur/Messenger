package com.ua.nure.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.command.Command;
import com.ua.nure.util.ConnectionConstants;
import com.ua.nure.util.Namings;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Exchanger;

@Component
public class MessengerServer {

    private ServerSocket serverSocket;

    private final int port;
    private final Map<String, Command> commands;
    private final List<ClientListener> clients;
    private final ObjectMapper jsonFormatter;
    private final int maxQuantityOfClients;

    @Autowired
    public MessengerServer(ConfigurableApplicationContext context) {
        commands = (Map<String, Command>) context.getBean(Namings.SERVER_COMMANDS);
        clients = new ArrayList<>();
        jsonFormatter = new ObjectMapper();
        port = ConnectionConstants.PORT;
        maxQuantityOfClients = ConnectionConstants.MAX_NUMBER_OF_CLIENTS;
    }

    public class ClientListener implements Closeable {

        private final Socket socket;

        private final DataInputStream reader;
        @Getter
        private final DataOutputStream writer;
        @Getter
        private final Map<String, Object> session;
        private final Exchanger<ClientPackage> responseExchanger;

        private final RequestHandler requestHandler;
        private final ResponseHandler responseHandler;

        private class RequestHandler extends Thread {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    while (!socket.isClosed()) {
                        ClientPackage response = new ClientPackage();
                        try {
                            String jsonString = reader.readUTF();
                            ServerPackage request = jsonFormatter.readValue(jsonString, ServerPackage.class);
                            Command command = commands.get(request.getCommandName());
                            if (command == null) {
                                throw new CommandException("There are no such command");
                            }
                            response = command.execute(session, request.getAttributes());
                        } catch (IOException e) {
                            response.setExceptionMessage("505! Error from server");
                        } catch (CommandException e) {
                            response.setExceptionMessage(e.getMessage());
                        }
                        responseExchanger.exchange(response);
                    }
                } catch (InterruptedException e) {
                    close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        private class ResponseHandler extends Thread {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    while (!socket.isClosed()) {
                        ClientPackage response = responseExchanger.exchange(null);
                        String jsonResponse = jsonFormatter.writeValueAsString(response);
                        if (response.getReceiversId().isEmpty()) {
                            synchronized (writer) {
                                writer.writeUTF(jsonResponse);
                            }
                            continue;
                        }
                        for (long receiverId : response.getReceiversId()) {
                            for (DataOutputStream receiverWriter : getOutputStreamsByUserId(receiverId)) {
                                synchronized (receiverWriter) {
                                    receiverWriter.writeUTF(jsonResponse);
                                }
                            }
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }

            private List<DataOutputStream> getOutputStreamsByUserId(long id) {
                List<DataOutputStream> streams = new ArrayList<>();
                for (ClientListener client : clients) {
                    User user = (User) client.getSession().get(Namings.MAIN_USER);
                    if (user != null && user.getId() == id) {
                        streams.add(client.getWriter());
                    }
                }
                return streams;
            }
        }

        public ClientListener(Socket socket) throws IOException {
            this.socket = socket;
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            session = new HashMap<>();
            responseExchanger = new Exchanger<>();

            requestHandler = new RequestHandler();
            responseHandler = new ResponseHandler();
            requestHandler.start();
            responseHandler.start();
        }

        @Override
        public void close() throws IOException {
            requestHandler.interrupt();
            responseHandler.interrupt();

            reader.close();
            writer.close();
            if (!socket.isClosed()) {
                socket.close();
            }
            clients.remove(this);
        }
    }

    private void runServer() throws IOException {
        while (!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            if (clients.size() == maxQuantityOfClients) {
                clientSocket.close();
            } else {
                clients.add(new ClientListener(clientSocket));
            }
        }
    }

    public void startServer() throws IOException {
        if (serverSocket != null) {
            throw new IllegalArgumentException();
        }
        serverSocket = new ServerSocket(port);
        runServer();
    }
}