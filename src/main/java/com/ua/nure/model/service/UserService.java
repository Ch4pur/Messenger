package com.ua.nure.model.service;

import com.ua.nure.model.entity.User;

public interface UserService {
    User getUserById(long id);

    User getUserByLogin(String login);

    void addUser(User user);

    void updateUser(User user);

    void removeUserById(long id);
}
