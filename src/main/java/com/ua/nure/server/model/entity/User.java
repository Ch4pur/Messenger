package com.ua.nure.server.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
@JsonRootName("user")
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

    @Column(name = "username")
    @Size(max = 20, message = "Username length must be from 3 to 20")
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

    @JsonIgnore
    public String getUsernameOrLogin() {
        return username == null || username.isEmpty() ? login : username;
    }
}


