package com.ua.nure.server.model.service.impl;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.server.model.repository.UserRepository;
import com.ua.nure.server.model.service.UserService;
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
        return userRepository.getById(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public User signInUser(String login, String password) throws ServiceException {
        User user = getUserByLogin(login);
        if (user == null) {
            throw new ServiceException("User with specified login doesn't exist");
        }
        if (!user.getPassword().equals(password)) {
            throw new ServiceException("Wrong password");
        }

        return user;
    }

    @Override
    public User signUpUser(@NotNull User user) throws ServiceException {
        if (userRepository.existsById(user.getId())) {
            throw new ServiceException("Specified user already exists");
        }
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new ServiceException("This login is already used");
        }

        return userRepository.save(user);
    }

    @Override
    public void updateUser(@NotNull User user) throws ServiceException {
        if (!userRepository.existsById(user.getId())) {
            throw new ServiceException("Specified user doesn't exist");
        }
        userRepository.save(user);
    }

    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }
}
