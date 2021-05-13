package com.ua.nure.server.command;

import com.ua.nure.exception.CommandException;
import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.Message;
import com.ua.nure.model.service.MessageService;
import com.ua.nure.model.service.RoomService;
import com.ua.nure.server.ResponsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GetRoomMessagesCommand implements Command{

    private final RoomService roomService;
    private final MessageService messageService;

    @Autowired
    public GetRoomMessagesCommand(RoomService roomService, MessageService messageService) {
        this.roomService = roomService;
        this.messageService = messageService;
    }

    @Override
    public ResponsePackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        long roomId = (long) attributes.get("roomId");

        try {
            List<Message> messageList = messageService.getMessagesByRoomId(roomId);

            ResponsePackage responsePackage = new ResponsePackage();

            responsePackage.setCommandName("toRoom");

            responsePackage.addAttribute("room", roomId);
            responsePackage.addAttribute("messages", messageList);

            return responsePackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
