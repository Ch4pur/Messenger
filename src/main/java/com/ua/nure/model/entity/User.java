package com.ua.nure.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@javax.persistence.Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "login", unique = true, updatable = false, nullable = false)
    @Size(min = 3, max = 20, message = "Login length must be from 3 to 20")
    private String login;

    @Column(name = "username" )
    @Size(min = 3, max = 20, message = "Username length must be from 3 to 20")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 30, message = "Password length must be from 5 to 30")
    private String password;
}


