package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.RoomService;
import com.ua.nure.data.ClientPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.ua.nure.util.Namings.*;
import static com.ua.nure.util.ClientCommandNames.UPDATE_ROOMS_PANE;

@Component
public class CreateDialogRoomCommand implements Command {

    private final RoomService roomService;

    @Autowired
    public CreateDialogRoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        long roommateId = (long) attributes.get(OTHER_USER_ID);
        long userId = ((User) session.get(MAIN_USER)).getId();

        try {
            roomService.createDialog(userId,roommateId);
            ClientPackage clientPackage = new ClientPackage();

            clientPackage.setCommandName(UPDATE_ROOMS_PANE);
            clientPackage.addReceiverId(userId);
            clientPackage.addReceiverId(roommateId);

            return clientPackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
