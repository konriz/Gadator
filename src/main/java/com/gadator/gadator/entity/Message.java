package com.gadator.gadator.entity;

import java.util.Date;

public abstract class Message {

    private Integer id;

    private User user;

    private Date sentDate;

    private Object content;

    private Conversation conversation;

}
