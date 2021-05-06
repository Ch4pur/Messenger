package com.ua.nure.model.service;

import com.ua.nure.model.entity.Room;

public interface RoomService {
    void createRoom(Room room);

    void removeRoomById(long id);

    void updateRoom(Room room);

    void getRoomsByUserId(long id);
}
