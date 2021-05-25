package com.ua.nure.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.client.Client;
import com.ua.nure.client.application.ClientMain;
import com.ua.nure.client.util.Util;
import com.ua.nure.util.ConnectionConstants;

public class ApplicationContext {

    private Client client;
    private int port;
    private String host;

    private ObjectMapper objectMapper;

    private String startingPagePath;

    public static ApplicationContext createContext() {
        ApplicationContext context = new ApplicationContext();
        context.config();

        return context;
    }

    private void config() {
        initClient();
        setStartingPath();
    }

    private void initClient() {
        objectMapper = new ObjectMapper();
        port = ConnectionConstants.PORT;
        host = ConnectionConstants.HOST;

        client = new Client();
        client.setHost(host);
        client.setPort(port);
        client.setJsonMapper(objectMapper);

        ClientMain.setClient(client);
    }

    private void setStartingPath() {
        startingPagePath = Util.SIGN_IN_PAGE_PATH;

        ClientMain.setStartingPath(startingPagePath);
    }
}
