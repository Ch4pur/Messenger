package com.ua.nure.model.service;

import com.ua.nure.model.entity.Message;

import java.util.List;

public interface MessageService {
    void sentMessage(Message message);

    void removeMessageById(long id);

    void editMessageById(long id,String newContent);

    List<Message> getMessagesBySenderId(long id);

    List<Message> getMessagesByMemberId(long id);

    List<Message> getMessagesByContentPartAndRoomId(String content, long roomId);
}
