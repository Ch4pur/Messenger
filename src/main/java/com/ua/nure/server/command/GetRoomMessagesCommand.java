package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Message;
import com.ua.nure.server.model.service.MessageService;
import com.ua.nure.server.model.service.RoomService;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.util.ClientCommandNames;
import com.ua.nure.util.Namings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GetRoomMessagesCommand implements Command {

    private final MessageService messageService;

    @Autowired
    public GetRoomMessagesCommand(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        long roomId = (int) attributes.get(Namings.ROOM_ID);

        try {
            List<Message> messageList = messageService.getMessagesByRoomId(roomId);

            ClientPackage responsePackage = new ClientPackage();

            responsePackage.setCommandName(ClientCommandNames.GET_MESSAGES);

            responsePackage.addAttribute(Namings.ROOM_ID, roomId);
            responsePackage.addAttribute(Namings.MESSAGES, messageList);

            return responsePackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
