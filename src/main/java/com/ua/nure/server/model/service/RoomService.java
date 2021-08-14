package com.ua.nure.server.model.service;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Room;

import java.util.List;

public interface RoomService {
    //TODO implement this functional
    void createRoom(Room room) throws ServiceException;

    Room createDialog(String firstUserLogin, String secondUserLogin) throws ServiceException;
    //TODO implement this functional
    void removeRoomById(long id);
    //TODO implement this functional
    void updateRoom(Room room) throws ServiceException;
    //TODO implement this functional
    Room getRoomById(long id);

    List<Room> getRoomsByUserId(long id) throws ServiceException;
}
