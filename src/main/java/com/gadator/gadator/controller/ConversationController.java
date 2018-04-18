package com.gadator.gadator.controller;

import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class ConversationController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<TextMessage> getMessages()
    {
        return messageRepository.findAll();
    }

    @GetMapping("add")
    public TextMessage addMessage()
    {
        TextMessage message = new TextMessage();
        message.setUser("Konriz");
        message.setSentDate(new Date());
        message.setContent("Hello World!");
        messageRepository.save(message);
        return message;
    }


}
