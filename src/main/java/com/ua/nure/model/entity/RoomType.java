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
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "room_types")
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_type_name", nullable = false, unique = true, updatable = false)
    @Size(min = 5, max = 30, message = "Name length must be from 5 to 30")
    private String name;

    @Column(name = "max_number_of_people", nullable = false)
    @Min(value = 1, message = "Quantity of members must be positive and starting from 1")
    private int maxQuantityOfMembers;
}
