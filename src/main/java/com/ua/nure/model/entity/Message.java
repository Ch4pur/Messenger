package com.ua.nure.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@javax.persistence.Entity
@Table(
        name = "messages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "sender_id", "sent_date"})
)
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne()
    @JoinColumn(name = "replied_message_id")
    private Message repliedMessage;

    @Column(name = "content")
    @NotEmpty
    private String content;

    @Column(name = "sent_date")
    private Timestamp date;

    @Column(name = "is_read")
    private boolean isChecked;

    @Column(name = "is_edited")
    private boolean isEdited;

}
