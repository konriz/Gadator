package com.gadator.messages.entities;

import com.gadator.conversations.entities.Conversation;
import com.gadator.users.entities.User;

import java.util.Date;

public abstract class Message {

    private Integer id;

    private User user;

    private Date sentDate;

    private Object content;

    private Conversation conversation;

}
