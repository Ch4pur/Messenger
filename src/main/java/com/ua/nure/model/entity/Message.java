package com.ua.nure.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "sent_date"})
)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    @JsonProperty
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @JsonProperty
    private Member member;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "replied_message_id")
    @JsonProperty
    private Message repliedMessage;

    @Column(name = "content")
    @NotEmpty
    @JsonProperty
    private String content;

    @Column(name = "sent_date")
    @JsonProperty
    private Timestamp date;

    @Column(name = "is_read")
    @JsonProperty
    private boolean isChecked;

    @Column(name = "is_edited")
    @JsonProperty
    private boolean isEdited;

}
