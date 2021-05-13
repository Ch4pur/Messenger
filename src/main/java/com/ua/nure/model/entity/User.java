package com.ua.nure.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty
    private long id;

    @Column(name = "login", unique = true, updatable = false, nullable = false)
    @Size(min = 3, max = 20, message = "Login length must be from 3 to 20")
    @JsonProperty
    private String login;

    @Column(name = "username" )
    @Size(min = 3, max = 20, message = "Username length must be from 3 to 20")
    @JsonProperty
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 30, message = "Password length must be from 5 to 30")
    @JsonProperty
    private String password;

    public User(String login, String username, String password) {
        this.login = login;
        this.username = username;
        this.password = password;
    }
}


