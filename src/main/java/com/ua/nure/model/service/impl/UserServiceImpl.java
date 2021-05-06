package com.ua.nure.model.service.impl;

import com.ua.nure.model.entity.User;
import com.ua.nure.model.repository.UserRepository;
import com.ua.nure.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public void addUser(@NotNull User user) {
        if (userRepository.existsById(user.getId())) {
            //TODO custom exception
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(@NotNull User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
        }
    }

    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }
}
