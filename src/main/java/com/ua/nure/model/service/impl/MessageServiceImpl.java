package com.ua.nure.model.service.impl;

import com.ua.nure.model.entity.Message;
import com.ua.nure.model.entity.Role;
import com.ua.nure.model.repository.*;
import com.ua.nure.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository,
                              MemberRepository memberRepository, RoomRepository roomRepository,
                              RoleRepository roleRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.roomRepository = roomRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void sentMessage(@NotNull Message message) {
        long memberId = message.getMember().getId();
        if (!(memberRepository.existsById(memberId))) {
            //TODO custom exception
        }
        Role role = roleRepository.getRoleByMemberId(memberId);
        if (!role.isCanWrite()) {
            //TODO custom exception
        }
        Message repliedMessage = message.getRepliedMessage();
        if (repliedMessage != null && messageRepository.existsById(repliedMessage.getId())) {
            //TODO custom exception
        }
        if (messageRepository.existsById(message.getId())) {
            //TODO custom exception
        }
        messageRepository.save(message);
    }

    @Override
    public void removeMessageById(long id) {
        if (!messageRepository.existsById(id)) {
            //TODO custom exception
        }
        Message message = messageRepository.getOne(id);
        Role role = message.getMember().getRole();
        if (!role.isCanRemove()) {
            //TODO custom exception
        }
        messageRepository.deleteById(id);
    }

    @Override
    public void editMessageById(long id, @NotNull String newContent) {
        if (messageRepository.existsById(id)) {
            //TODO custom exception
        }
        if (newContent.isBlank()) {
            //TODO custom exception
        }
        Message message = messageRepository.getOne(id);
        Role role = message.getMember().getRole();
        if (!role.isCanEdit()) {
            //TODO custom exception
        }
        messageRepository.editMessage(id, newContent);
    }

    @Override
    public List<Message> getMessagesBySenderId(long id) {
        if (!userRepository.existsById(id)) {
            //TODO custom exception
        }

        return messageRepository.getMessagesBySenderId(id);
    }

    @Override
    public List<Message> getMessagesByMemberId(long id) {
        if (!memberRepository.existsById(id)) {
            //TODO custom exception
        }

        return messageRepository.getMessagesByMemberId(id);
    }

    @Override
    public List<Message> getMessagesByContentPartAndRoomId(@NotNull String content, long roomId) {
        if (!roomRepository.existsById(roomId)) {
            //TODO custom exception
        }
        return messageRepository.getMessagesByContentPartAndRoomId(content, roomId);
    }
}
