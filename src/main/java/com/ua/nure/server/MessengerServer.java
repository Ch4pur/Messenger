package com.ua.nure.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.exception.CommandException;
import com.ua.nure.model.entity.User;
import com.ua.nure.server.command.Command;
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
import java.net.SocketException;
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

    @Autowired
    public MessengerServer(ConfigurableApplicationContext context) {
        port = (int) context.getBean("port");
        commands = (Map<String, Command>) context.getBean("serverCommands");
        clients = new ArrayList<>();
        jsonFormatter = new ObjectMapper();
    }

    public class ClientListener implements Closeable {

        private final Socket socket;

        private final DataInputStream reader;
        @Getter
        private final DataOutputStream writer;
        @Getter
        private final Map<String, Object> session;
        private final Exchanger<ResponsePackage> responseExchanger;

        private class RequestHandler extends Thread {
            @SneakyThrows
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    ResponsePackage response = new ResponsePackage();
                    try {
                        String jsonString = reader.readUTF();
                        RequestPackage request = jsonFormatter.readValue(jsonString, RequestPackage.class);
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
                close();
            }
        }

        private class ResponseHandler extends Thread {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    while (!socket.isClosed()) {
                        ResponsePackage response = responseExchanger.exchange(null);
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
                } catch (SocketException e) {
                    close();
                }
            }
        }

        public ClientListener(Socket socket) throws IOException {
            this.socket = socket;
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            session = new HashMap<>();
            responseExchanger = new Exchanger<>();

            RequestHandler requestHandler = new RequestHandler();
            ResponseHandler responseHandler = new ResponseHandler();
            requestHandler.start();
            responseHandler.start();
        }

        @Override
        public void close() throws IOException {
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
            clients.add(new ClientListener(clientSocket));
        }
    }

    public void startServer() throws IOException {
        if (serverSocket != null) {
            throw new IllegalArgumentException();
        }
        serverSocket = new ServerSocket(port);
        runServer();
    }

    private List<DataOutputStream> getOutputStreamsByUserId(long id) {
        List<DataOutputStream> streams = new ArrayList<>();
        for (ClientListener client : clients) {
            User user = (User) client.getSession().get("user");
            if (user != null && user.getId() == id) {
                streams.add(client.getWriter());
            }
        }
        return streams;
    }
}