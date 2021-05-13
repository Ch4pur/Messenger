package com.ua.nure.server.command;

import com.ua.nure.exception.CommandException;
import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.Member;
import com.ua.nure.model.entity.Message;
import com.ua.nure.model.entity.User;
import com.ua.nure.model.service.MemberService;
import com.ua.nure.model.service.MessageService;
import com.ua.nure.server.ResponsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public ResponsePackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException {

        User user = (User) session.get("user");
        long roomId = (int) attributes.get("roomId");
        String content = (String) attributes.get("content");

        try {
            Member member = memberService.getMemberByRoomIdAndUserId(user.getId(), roomId);
            Message message = new Message();
            message.setContent(content);
            message.setMember(member);
            message.setDate(Timestamp.valueOf(LocalDateTime.now()));

            messageService.sendMessage(message);

            ResponsePackage responsePackage = new ResponsePackage();
            List<Member> memberList = memberService.getMembersByRoomId(roomId);
            System.out.println(memberList);
            for (Member roomMember : memberList) {
                User roomUser = roomMember.getUser();
                responsePackage.addReceiverId(roomUser.getId());
            }

            responsePackage.setCommandName("updateRoom");
            responsePackage.addAttribute("newMessage", message);

            return responsePackage;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
