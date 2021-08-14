package com.ua.nure.server.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
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
@JsonRootName("message")
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
