package com.ua.nure.server.config;

import com.ua.nure.server.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.ua.nure.util.ServerCommandNames.*;

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

        commands.put(SIGN_IN, context.getBean(SignInCommand.class));
        commands.put(SIGN_UP, context.getBean(SignUpCommand.class));
        commands.put(SEND_MESSAGE, context.getBean(SendMessageCommand.class));
        commands.put(GET_MESSAGES, context.getBean(GetRoomMessagesCommand.class));
        commands.put(CREATE_DIALOG, context.getBean(CreateDialogRoomCommand.class));
        commands.put(GET_ROOMS, context.getBean(GetUserRoomsCommand.class));

        return commands;
    }
}
