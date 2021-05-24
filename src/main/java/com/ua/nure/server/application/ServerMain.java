package com.ua.nure.server.application;

import com.ua.nure.server.MessengerServer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ua.nure.server")
@EnableJpaRepositories("com.ua.nure.server.model.repository")
@EntityScan(basePackages = "com.ua.nure.server.model.entity")
public class ServerMain {

    private static MessengerServer server;

    @Autowired
    public ServerMain(MessengerServer server) {
        ServerMain.server = server;
    }

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
        server.startServer();
    }
}