package com.ua.nure.server.config;

import com.ua.nure.server.command.Command;
import com.ua.nure.server.command.CreateDialogRoomCommand;
import com.ua.nure.server.command.GetRoomMessagesCommand;
import com.ua.nure.server.command.GetUserRoomsCommand;
import com.ua.nure.server.command.SendMessageCommand;
import com.ua.nure.server.command.SignInCommand;
import com.ua.nure.server.command.SignUpCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.ua.nure.util.ServerCommandNames.CREATE_DIALOG;
import static com.ua.nure.util.ServerCommandNames.GET_MESSAGES;
import static com.ua.nure.util.ServerCommandNames.GET_ROOMS;
import static com.ua.nure.util.ServerCommandNames.SEND_MESSAGE;
import static com.ua.nure.util.ServerCommandNames.SIGN_IN;
import static com.ua.nure.util.ServerCommandNames.SIGN_UP;


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
