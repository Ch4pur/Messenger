package com.ua.nure.model.service;

import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.User;

public interface UserService {
    User getUserById(long id);

    User getUserByLogin(String login);

    User signInUser(String login, String password) throws ServiceException;

    void addUser(User user) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    void removeUserById(long id);
}
