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
public class SignInCommand implements Command {

    private final UserService userService;

    @Autowired
    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponsePackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        String login = (String) attributes.get("login");
        String password = (String) attributes.get("password");
        try {
            User user = userService.signInUser(login, password);

            session.put("user", user);

            ResponsePackage responsePackage = new ResponsePackage();
            responsePackage.putCacheChange("user", user);
            responsePackage.setCommandName("toMainPage");

            return responsePackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
