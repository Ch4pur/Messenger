package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Room;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.RoomService;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.server.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.ua.nure.util.ClientCommandNames.ADD_ROOM;
import static com.ua.nure.util.Namings.MAIN_USER;
import static com.ua.nure.util.Namings.NEW_ROOM;
import static com.ua.nure.util.Namings.OTHER_USER_LOGIN;


@Component
public class CreateDialogRoomCommand implements Command {

    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public CreateDialogRoomCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    public ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        String otherUserLogin = (String) attributes.get(OTHER_USER_LOGIN);
        User mainUser = (User) session.get(MAIN_USER);

        try {
            User otherUser = userService.getUserByLogin(otherUserLogin);
            if (otherUser == null) {
                throw new CommandException("No user with such login");
            }
            Room newRoom = roomService.createDialog(mainUser.getLogin(), otherUser.getLogin());
            ClientPackage clientPackage = new ClientPackage();

            clientPackage.setCommandName(ADD_ROOM);
            clientPackage.addReceiverId(mainUser.getId());
            clientPackage.addReceiverId(otherUser.getId());
            clientPackage.addAttribute(NEW_ROOM, newRoom);

            return clientPackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
