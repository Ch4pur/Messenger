package com.ua.nure.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String name;

    @Column(name = "can_write")
    private boolean canWrite;

    @Column(name = "can_invite")
    private boolean canInvite;

    @Column(name = "can_edit")
    private boolean canEdit;

    @Column(name = "can_remove")
    private boolean canRemove;
}
