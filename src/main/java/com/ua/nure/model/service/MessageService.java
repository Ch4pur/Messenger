package com.ua.nure.model.service;

import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message) throws ServiceException;

    void removeMessageById(long id) throws ServiceException;

    void editMessageById(long id,String newContent) throws ServiceException;

    List<Message> getMessagesByRoomId(long id) throws ServiceException;

    List<Message> getMessagesByMemberId(long id) throws ServiceException;

    List<Message> getMessagesByContentPartAndRoomId(String content, long roomId) throws ServiceException;
}
