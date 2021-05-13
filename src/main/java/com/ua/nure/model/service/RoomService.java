package com.ua.nure.model.service;

import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.Room;

import java.util.List;

public interface RoomService {
    void createRoom(Room room) throws ServiceException;

    void createDialog(long firstUserId, long secondUserId) throws ServiceException;

    void removeRoomById(long id);

    void updateRoom(Room room) throws ServiceException;

    Room getRoomById(long id);

    List<Room> getRoomsByUserId(long id) throws ServiceException;
}
