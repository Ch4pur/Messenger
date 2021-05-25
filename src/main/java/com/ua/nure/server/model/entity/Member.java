package com.ua.nure.server.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "room_members")
@JsonRootName("message")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @JsonProperty
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonProperty
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @Column(name = "can_write")
    @JsonProperty
    private boolean canWrite;

    @Column(name = "can_invite")
    @JsonProperty
    private boolean canInvite;

    @Column(name = "can_edit")
    @JsonProperty
    private boolean canEdit;

    @Column(name = "can_remove")
    @JsonProperty
    private boolean canRemove;
}
