package com.ua.nure.server.command;

import com.ua.nure.client.Client;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Room;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.RoomService;
import com.ua.nure.server.model.service.UserService;
import com.ua.nure.util.ClientCommandNames;
import com.ua.nure.util.Namings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public class GetUserRoomsCommand implements Command{

    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public GetUserRoomsCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    public ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        User user = (User) session.get(Namings.MAIN_USER);
        try {
            user = userService.getUserById(user.getId());
            List<Room> rooms = roomService.getRoomsByUserId(user.getId());

            ClientPackage clientPackage = new ClientPackage();
            clientPackage.setCommandName(ClientCommandNames.UPDATE_ROOMS_PANE);
            clientPackage.addAttribute(Namings.ROOMS, rooms);
            System.out.println(rooms);
            return clientPackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
