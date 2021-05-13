package com.ua.nure.application;

import com.ua.nure.server.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationBeans {

    private final ConfigurableApplicationContext context;

    @Autowired
    public ApplicationBeans(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Bean
    public Map<String, Command> serverCommands() {
        Map<String, Command> commands = new HashMap<>();

        commands.put("signIn", context.getBean(SignInCommand.class));
        commands.put("signUp", context.getBean(SignUpCommand.class));
        commands.put("sendMessage", context.getBean(SendMessageCommand.class));
        commands.put("getRoomMessages", context.getBean(GetRoomMessagesCommand.class));

        return commands;
    }

    @Bean
    public int port() {
        return 3030;
    }

    @Bean
    public String host() {
        return "localhost";
    }
}
