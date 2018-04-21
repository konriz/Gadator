package com.gadator.gadator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "messages")
@Setter
@Entity
public class TextMessage extends Message<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "sent_date")
    private Date sentDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public TextMessage()
    {

    }

    public TextMessage(User user, String content, Conversation conversation)
    {
        this.setUser(user);
        this.setContent(content);
        this.setSentDate(new Date());
        this.setConversation(conversation);

    }
}