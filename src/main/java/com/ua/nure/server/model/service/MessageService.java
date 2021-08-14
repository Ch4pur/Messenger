package com.ua.nure.server.model.service;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message) throws ServiceException;
    //TODO implement this functional
    void removeMessageById(long id) throws ServiceException;
    //TODO implement this functional
    void editMessageById(long id,String newContent) throws ServiceException;

    List<Message> getMessagesByRoomId(long id) throws ServiceException;
    //TODO implement this functional
    List<Message> getMessagesByMemberId(long id) throws ServiceException;
    //TODO implement this functional
    List<Message> getMessagesByContentPartAndRoomId(String content, long roomId) throws ServiceException;
}
