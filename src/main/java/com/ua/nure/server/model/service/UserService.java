package com.ua.nure.server.model.service;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.User;

public interface UserService {
    User getUserById(long id);

    User getUserByLogin(String login);

    User signInUser(String login, String password) throws ServiceException;

    User signUpUser(User user) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    void removeUserById(long id);
}
