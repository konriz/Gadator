package com.gadator.gadator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "messages")
@Setter
@Entity
public class TextMessage {

    @Id
    @GeneratedValue
    private Integer id;

    private String user;

    private Date sentDate;

    private String content;

}
