package com.ua.nure.server.model.service;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Room;

import java.util.List;

public interface RoomService {
    void createRoom(Room room) throws ServiceException;

    Room createDialog(String firstUserLogin, String secondUserLogin) throws ServiceException;

    void removeRoomById(long id);

    void updateRoom(Room room) throws ServiceException;

    Room getRoomById(long id);

    List<Room> getRoomsByUserId(long id) throws ServiceException;
}
