package com.ua.nure.server.command;

import com.ua.nure.client.util.Util;
import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.UserService;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.util.ClientCommandNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.ua.nure.util.Namings.*;

@Component
public class SignUpCommand implements Command {

    private final UserService userService;

    @Autowired
    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        String login = (String) attributes.get(LOGIN);
        String password = (String) attributes.get(PASSWORD);
        String username = (String) attributes.get(USERNAME);

        User user = new User(login, username, password);
        try {
            user = userService.signUpUser(user);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }

        session.put(MAIN_USER, user);

        ClientPackage responsePackage = new ClientPackage();
        responsePackage.setCommandName(ClientCommandNames.SWITCH_PANE);
        responsePackage.addAttribute(PATH, Util.MAIN_PAGE_PATH);

        return responsePackage;
    }
}
