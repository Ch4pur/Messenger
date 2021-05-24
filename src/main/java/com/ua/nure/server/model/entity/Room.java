package com.ua.nure.server.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    @JsonProperty
    private long id;

    @Column(name = "room_title", nullable = false)
    @Size(min = 1, max = 40, message = "Title length must be from 1 to 40")
    @JsonProperty
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_members",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonProperty
    private List<User> users;

    @Column(name = "max_number_of_people", nullable = false)
    @Min(value = 1, message = "Quantity of members must be positive and starting from 1")
    @JsonProperty
    private int maxQuantityOfMembers;
}