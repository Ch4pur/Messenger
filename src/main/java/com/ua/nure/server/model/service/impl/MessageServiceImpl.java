package com.ua.nure.server.model.service.impl;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Message;

import com.ua.nure.server.model.repository.MemberRepository;
import com.ua.nure.server.model.repository.MessageRepository;
import com.ua.nure.server.model.repository.RoomRepository;
import com.ua.nure.server.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              MemberRepository memberRepository,
                              RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;
        this.roomRepository = roomRepository;

    }

    @Override
    public void sendMessage(@NotNull Message message) throws ServiceException {
        long memberId = message.getMember().getId();
        if (!(memberRepository.existsById(memberId))) {
            throw new ServiceException("Specified member doesn't exist");
        }
        Message repliedMessage = message.getRepliedMessage();
        if (repliedMessage != null && messageRepository.existsById(repliedMessage.getId())) {
            throw new ServiceException("Specified replied message doesn't exist");
        }
        if (!message.getMember().isCanWrite()) {
            throw new ServiceException("Specified role doesn't exist");
        }

        if (messageRepository.existsById(message.getId())) {
            throw new ServiceException("Specified message already exists");
        }
        messageRepository.save(message);
    }

    @Override
    public void removeMessageById(long id) throws ServiceException {
        if (!messageRepository.existsById(id)) {
            throw new ServiceException("Specified message doesn't exist");
        }
        Message message = messageRepository.getById(id);
        if (!message.getMember().isCanRemove()) {
            throw new ServiceException("You dont have sufficient rights to remove message");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public void editMessageById(long id, @NotNull String newContent) throws ServiceException {
        if (!messageRepository.existsById(id)) {
            throw new ServiceException("Specified message doesn't exist");
        }
        if (newContent.isBlank()) {
            throw new ServiceException("Content can not be blanc");
        }
        Message message = messageRepository.getById(id);
        if (!message.getMember().isCanEdit()) {
            throw new ServiceException("You dont have sufficient rights to edit message");
        }
        messageRepository.editMessage(id, newContent);
    }

    @Override
    public List<Message> getMessagesByRoomId(long id) throws ServiceException {
        if (!roomRepository.existsById(id)) {
            throw new ServiceException("Nonexistent room");
        }
        return messageRepository.getMessagesByRoomId(id);
    }


    @Override
    public List<Message> getMessagesByMemberId(long id) throws ServiceException {
        if (!memberRepository.existsById(id)) {
            throw new ServiceException("Specified member doesn't exist");
        }

        return messageRepository.getMessagesByMemberId(id);
    }

    @Override
    public List<Message> getMessagesByContentPartAndRoomId(@NotNull String content, long roomId) throws ServiceException {
        if (!roomRepository.existsById(roomId)) {
            throw new ServiceException("Specified room doesn't exist");
        }
        return messageRepository.getMessagesByContentPartAndRoomId(content, roomId);
    }
}
