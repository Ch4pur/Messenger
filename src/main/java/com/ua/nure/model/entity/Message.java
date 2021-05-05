package com.ua.nure.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@javax.persistence.Entity
@Table(
        name = "messages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id","receiver_id","sender_id","sent_date"})
)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id", nullable = false)
    private User receiver;

    @ManyToOne()
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id", nullable = false)
    private User sender;

    @OneToOne()
    @JoinColumn(name = "replied_message_id")
    private Message repliedMessage;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_date")
    private Timestamp date;

    @Column(name = "is_read")
    private boolean isChecked;

    @Column(name = "is_edited")
    private boolean isEdited;

}
