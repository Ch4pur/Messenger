package com.ua.nure.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.nure.client.Client;
import com.ua.nure.client.annotation.Bean;
import com.ua.nure.client.parser.Parser;


import java.util.Arrays;

import static com.ua.nure.client.util.Util.SIGN_IN_PAGE_PATH;
import static com.ua.nure.util.ConnectionConstants.HOST;
import static com.ua.nure.util.ConnectionConstants.PORT;


public class ApplicationContext {

    private int port;
    private String host;

    private ObjectMapper objectMapper;

    @Bean
    private final String startingPagePath;
    @Bean
    private final Parser parser;
    @Bean
    private Client client;

    private ApplicationContext() {
        initClient();
        startingPagePath = SIGN_IN_PAGE_PATH;
        parser = new Parser(objectMapper);
    }

    public static ApplicationContext createContext() {
        return new ApplicationContext();
    }

    public Object getBean(String name) {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Bean.class) && f.getName().equals(name))
                .map(f-> {
                    try {
                        return f.get(this);
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .findFirst()
                .orElse(null);
    }

    private void initClient() {
        objectMapper = new ObjectMapper();
        port = PORT;
        host = HOST;

        client = new Client();
        client.setHost(host);
        client.setPort(port);
        client.setJsonMapper(objectMapper);
    }
}
