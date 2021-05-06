package com.ua.nure.model.repository;

import com.ua.nure.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("From User where login = :login")
    User getUserByLogin(@Param("login") String login);
}
