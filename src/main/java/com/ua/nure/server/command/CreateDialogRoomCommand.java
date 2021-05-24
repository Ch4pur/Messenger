package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.MemberService;
import com.ua.nure.server.model.service.RoomService;
import com.ua.nure.data.ResponsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateDialogRoomCommand implements Command {

    private final RoomService roomService;
    private final MemberService memberService;

    @Autowired
    public CreateDialogRoomCommand(RoomService roomService, MemberService memberService) {
        this.roomService = roomService;
        this.memberService = memberService;
    }

    @Override
    public ResponsePackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {
        long roommateId = (long) attributes.get("userId");
        long userId = ((User) session.get("user")).getId();

        try {
            roomService.createDialog(userId,roommateId);
            ResponsePackage responsePackage = new ResponsePackage();

            responsePackage.setCommandName("updateRooms");
            responsePackage.addReceiverId(userId);
            responsePackage.addReceiverId(roommateId);

            return responsePackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
