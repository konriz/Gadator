package com.gadator.gadator.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public abstract class Message<T> {

    private Integer id;

    private User user;

    private Date sentDate;

    private T content;

    private Conversation conversation;

}
