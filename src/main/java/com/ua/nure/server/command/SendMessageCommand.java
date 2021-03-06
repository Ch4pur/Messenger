package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Member;
import com.ua.nure.server.model.entity.Message;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.service.MemberService;
import com.ua.nure.server.model.service.MessageService;
import com.ua.nure.data.ClientPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.ua.nure.util.ClientCommandNames.ADD_MESSAGE;
import static com.ua.nure.util.Namings.CONTENT;
import static com.ua.nure.util.Namings.MAIN_USER;
import static com.ua.nure.util.Namings.NEW_MESSAGE;
import static com.ua.nure.util.Namings.ROOM_ID;


@Component
public class SendMessageCommand implements Command {

    private final MemberService memberService;
    private final MessageService messageService;

    @Autowired
    public SendMessageCommand(MemberService memberService, MessageService messageService) {
        this.memberService = memberService;
        this.messageService = messageService;
    }

    @Override
    public ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {

        User user = (User) session.get(MAIN_USER);
        long roomId = (int) attributes.get(ROOM_ID);
        String content = (String) attributes.get(CONTENT);

        try {
            Member member = memberService.getMemberByRoomIdAndUserId(roomId, user.getId());
            Message message = new Message();
            message.setContent(content);
            message.setMember(member);
            message.setDate(Timestamp.valueOf(LocalDateTime.now()));

            messageService.sendMessage(message);

            ClientPackage responsePackage = new ClientPackage();
            List<Member> memberList = memberService.getMembersByRoomId(roomId);
            for (Member roomMember : memberList) {
                User roomUser = roomMember.getUser();
                responsePackage.addReceiverId(roomUser.getId());
            }

            responsePackage.setCommandName(ADD_MESSAGE);
            responsePackage.addAttribute(NEW_MESSAGE, message);
            responsePackage.addAttribute(MAIN_USER, user);
            responsePackage.addAttribute(ROOM_ID, roomId);

            return responsePackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
