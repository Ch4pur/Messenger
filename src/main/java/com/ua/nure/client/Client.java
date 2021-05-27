package com.ua.nure.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.client.annotation.CommandFromServer;
import com.ua.nure.client.controller.Controller;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.data.ClientPackage;
import lombok.Cleanup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Exchanger;

public class Client {

    private ObjectMapper jsonMapper;

    private int port;
    private String host;
    private Socket socket;

    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;

    private final Exchanger<ServerPackage> requestPackageExchanger;

    private Controller currentController;

    private class ResponseHandler extends Thread {
        @Override
        public void run() {
            try {
                @Cleanup DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                while (!socket.isClosed()) {
                    ServerPackage serverPackage = requestPackageExchanger.exchange(null);
                    String jsonString = jsonMapper.writeValueAsString(serverPackage);
                    writer.writeUTF(jsonString);
                }
            } catch (SocketException | InterruptedException e) {
                System.out.println(socket + " Response handler is closed");
            } catch (JsonProcessingException e) {
                System.out.println("Json error " + e.getMessage());
            } catch (IOException e) {
                System.out.println((e.getMessage()));
            }
        }
    }

    private class RequestHandler extends Thread {
        @Override
        public void run() {
            try {
                @Cleanup DataInputStream reader = new DataInputStream(socket.getInputStream());
                while (!socket.isClosed()) {
                    String jsonResponse = reader.readUTF();
                    ClientPackage clientPackage = jsonMapper.readValue(jsonResponse, ClientPackage.class);
                    String errorMessage = clientPackage.getExceptionMessage();
                    if (errorMessage != null) {
                        currentController.showError(errorMessage);
                    } else {
                        executeControllerCommand(clientPackage);
                    }
                }
            } catch (SocketException e) {
                System.out.println(socket + " Request handler is closed");
            } catch (JsonProcessingException e) {
                System.out.println("Json error " + e.getMessage());
            } catch (IOException e) {
                System.out.println((e.getMessage()));
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("Reflection error " + e.getMessage());
            }
        }

        private void executeControllerCommand(ClientPackage clientPackage) throws InvocationTargetException, IllegalAccessException {
            for (Method method : getAnnotatedFields(currentController.getClass())) {
                if (method.getAnnotation(CommandFromServer.class).value().equals(clientPackage.getCommandName())) {
                    method.setAccessible(true);
                    method.invoke(currentController,clientPackage);
                    method.setAccessible(false);
                }
            }
        }

        private List<Method> getAnnotatedFields(Class<?> clazz) {
            List<Method> res = new ArrayList<>();
            for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
                for (Method method : c.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(CommandFromServer.class)) {
                        res.add(method);
                    }
                }
            }
            return res;
        }
    }

    public Client() {
        requestPackageExchanger = new Exchanger<>();
    }

    public void connect() throws IOException {
        if (socket != null) {
            throw new IOException();
        }
        socket = new Socket(host, port);
        requestHandler = new RequestHandler();
        responseHandler = new ResponseHandler();

        requestHandler.start();
        responseHandler.start();
    }

    public void disconnect() throws IOException {
        if (socket == null) {
            throw new IOException();
        }
        socket.close();
        responseHandler.interrupt();
        requestHandler.interrupt();
    }

    public void sendPackageToServer(ServerPackage requestPackage) throws InterruptedException {
        requestPackageExchanger.exchange(requestPackage);
    }

    public void setCurrentController(Controller currentController) {
        this.currentController = currentController;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
