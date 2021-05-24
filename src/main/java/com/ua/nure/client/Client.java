package com.ua.nure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.client.controller.Controller;
import com.ua.nure.data.RequestPackage;
import com.ua.nure.data.ResponsePackage;
import com.ua.nure.util.ConnectionConstants;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Exchanger;

@Component
public class Client {

    private final ObjectMapper jsonMapper;

    private  Socket socket;

    private final RequestHandler requestHandler;
    private final ResponseHandler responseHandler;

    private final Exchanger<RequestPackage> requestPackageExchanger;
    private final Exchanger<ResponsePackage> responsePackageExchanger;

    private Controller currentController;

    private volatile boolean isRunning;

    private class ResponseHandler extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            @Cleanup DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            while (isRunning) {
                RequestPackage requestPackage = requestPackageExchanger.exchange(null);
                String jsonString = jsonMapper.writeValueAsString(requestPackage);
                writer.writeUTF(jsonString);
            }
        }
    }

    private class RequestHandler extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            @Cleanup DataInputStream reader = new DataInputStream(socket.getInputStream());
            while (isRunning) {
                String jsonResponse = reader.readUTF();
                ResponsePackage responsePackage = jsonMapper.readValue(jsonResponse, ResponsePackage.class);

                //TODO придумать как передать контроллеру
            }
        }
    }

    public Client()  {
        jsonMapper = new ObjectMapper();

        requestHandler = new RequestHandler();
        responseHandler = new ResponseHandler();

        requestPackageExchanger = new Exchanger<>();
        responsePackageExchanger = new Exchanger<>();
    }

    public void runClient() throws IOException {
        if (socket != null) {
            throw new IOException();
        }
        socket = new Socket(ConnectionConstants.HOST, ConnectionConstants.PORT);
        isRunning = true;
        requestHandler.start();
        responseHandler.start();
    }
    public void endClient() throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new IOException();
        }
        socket.close();
        isRunning = false;
        responseHandler.interrupt();
        requestHandler.interrupt();
    }

    public void sendPackageToServer(RequestPackage requestPackage) throws InterruptedException {
        requestPackageExchanger.exchange(requestPackage);
    }
}
