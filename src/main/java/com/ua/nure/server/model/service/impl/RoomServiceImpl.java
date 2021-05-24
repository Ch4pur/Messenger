package com.ua.nure.server.model.service.impl;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Room;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.repository.RoomRepository;
import com.ua.nure.server.model.repository.UserRepository;
import com.ua.nure.server.model.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomService, UserRepository userRepository) {
        this.roomRepository = roomService;
        this.userRepository = userRepository;
    }

    @Override
    public void createRoom(@NotNull Room room) throws ServiceException {
        if (roomRepository.existsById(room.getId())) {
            throw new ServiceException("The specified room already exists");
        }
        roomRepository.save(room);
    }

    @Override
    public void createDialog(long firstUserId, long secondUserId) throws ServiceException {
        if (!(userRepository.existsById(firstUserId) && userRepository.existsById(secondUserId))) {
            throw new ServiceException("Some of the users don't exist");
        }
        User firstUser = userRepository.getOne(firstUserId);
        User secondUser = userRepository.getOne(secondUserId);

        Room room = new Room(-1,"Dialog",List.of(firstUser,secondUser),2);
        roomRepository.save(room);
    }

    @Override
    public void removeRoomById(long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public void updateRoom(@NotNull Room room) throws ServiceException {
        if (roomRepository.existsById(room.getId())) {
            throw new ServiceException("The specified room doesn`t exist");
        }
        roomRepository.save(room);
    }

    @Override
    public Room getRoomById(long id) {
        return roomRepository.getOne(id);
    }


    @Override
    public List<Room> getRoomsByUserId(long id) throws ServiceException {
        if (!userRepository.existsById(id)) {
            throw new ServiceException("The specified user doesn`t exist");
        }
        return roomRepository.getRoomsByUserId(id);
    }
}
