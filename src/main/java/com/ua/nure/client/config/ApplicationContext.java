package com.ua.nure.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.client.Client;
import com.ua.nure.client.annotation.Bean;
import com.ua.nure.client.parser.Parser;
import com.ua.nure.client.util.Util;
import com.ua.nure.util.ConnectionConstants;


import java.util.Arrays;


public class ApplicationContext {

    private int port;
    private String host;

    private ObjectMapper objectMapper;

    @Bean
    private String startingPagePath;
    @Bean
    private Parser parser;
    @Bean
    private Client client;

    private ApplicationContext() {}

    public static ApplicationContext createContext() {
        ApplicationContext context = new ApplicationContext();
        context.config();

        return context;
    }

    public Object getBean(String name) {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Bean.class) && f.getName().equals(name))
                .map(f-> {
                    try {
                        return f.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .findFirst()
                .orElse(null);
    }

    private void config() {
        initClient();
        setStartingPath();
        initParser();
    }

    private void initClient() {
        objectMapper = new ObjectMapper();
        port = ConnectionConstants.PORT;
        host = ConnectionConstants.HOST;

        client = new Client();
        client.setHost(host);
        client.setPort(port);
        client.setJsonMapper(objectMapper);
    }

    private void setStartingPath() {
        startingPagePath = Util.SIGN_IN_PAGE_PATH;
    }

    private void initParser() {
        parser = new Parser(objectMapper);
    }
}
