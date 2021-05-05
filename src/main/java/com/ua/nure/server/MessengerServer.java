package com.ua.nure.server;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessengerServer {

    @Setter
    private int port;
    @Setter
    private int quantityOfClients = 1;

    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    @AllArgsConstructor
    private class CommandHandler implements Runnable {
        private final Socket socket;

        @Override
        public void run() {
            try (socket) {
                @Cleanup DataInputStream reader = new DataInputStream(socket.getInputStream());
                @Cleanup DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

                while (!socket.isClosed() && !serverSocket.isClosed()) {
                    /**
                     * TODO server handler logic
                     */
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void runServer() {
        try {
            serverSocket = new ServerSocket(port);
            threadPool = Executors.newFixedThreadPool(quantityOfClients);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new CommandHandler(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endServer() throws IOException {
        if (serverSocket == null) {
            //TODO make custom exceptions
            throw new RuntimeException();
        }
        serverSocket.close();
    }
}
