package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.UserService;
import com.ua.nure.data.ResponsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SignUpCommand implements Command {

    private final UserService userService;

    @Autowired
    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponsePackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        String login = (String) attributes.get("login");
        String password = (String) attributes.get("password");
        String username = (String) attributes.get("username");

        User user = new User(login, username, password);
        try {
            user = userService.signUpUser(user);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }

        session.put("user", user);
        ResponsePackage responsePackage = new ResponsePackage();
        responsePackage.setCommandName("toMainPage");
        responsePackage.putCacheChange("user", user);

        return responsePackage;
    }
}
