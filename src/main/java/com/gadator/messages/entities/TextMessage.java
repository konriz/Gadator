package com.gadator.messages.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gadator.conversations.entities.Conversation;
import com.gadator.users.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "messages")
@Setter
@Entity
public class TextMessage extends Message{

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
    {}

    @Override
    public String toString()
    {
            StringBuilder messageString = new StringBuilder();
            messageString.append(user.getName());
            messageString.append(" @ ");
            messageString.append(conversation.getName());
            messageString.append(" : ");
            messageString.append(getContent());
            return messageString.toString();
    }


}
