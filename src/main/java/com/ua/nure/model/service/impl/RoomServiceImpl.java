package com.ua.nure.model.service.impl;

import com.ua.nure.model.entity.Room;
import com.ua.nure.model.repository.RoomRepository;
import com.ua.nure.model.repository.RoomTypeRepository;
import com.ua.nure.model.repository.UserRepository;
import com.ua.nure.model.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomService,
                           UserRepository userRepository,
                           RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomService;
        this.userRepository = userRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public void createRoom(@NotNull Room room) {
        if (!roomTypeRepository.existsById(room.getRoomType().getId())) {
            //TODO custom exception
        }
        if (roomRepository.existsById(room.getId())) {
            //TODO custom exception
        }
        roomRepository.save(room);
    }

    @Override
    public void removeRoomById(long id) {
        if (!roomRepository.existsById(id)) {
            //TODO custom exception
        }
        roomRepository.deleteById(id);
    }

    @Override
    public void updateRoom(@NotNull Room room) {
        if (roomRepository.existsById(room.getId())) {
            //TODO custom exception
        }
        roomRepository.save(room);
    }


    @Override
    public void getRoomsByUserId(long id) {
        if (!userRepository.existsById(id)) {
            //TODO custom exception
        }
        roomRepository.getRoomsByUserId(id);
    }
}
