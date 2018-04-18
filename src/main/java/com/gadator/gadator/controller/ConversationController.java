package com.gadator.gadator.controller;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.repository.ConversationRepository;
import com.gadator.gadator.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ConversationController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @GetMapping()
    public List<String> getConversations()
    {
        List<String> conversationNames = new ArrayList<>();
        conversationRepository.findAll().stream().forEach(p -> conversationNames.add(p.getName()));

        return conversationNames;
    }

    @GetMapping("{cName}")
    public List<TextMessage> getMessages(@PathVariable("cName") String conversationName)
    {
        try
        {
            return messageRepository.findAllByConversation(conversationRepository.findOneByName(conversationName));
        }
        catch (NullPointerException e)
        {
            return new ArrayList<>();
        }
    }

    @GetMapping("add")
    public TextMessage addMessage(@RequestParam(defaultValue = "guest") String user,
                                  @RequestParam(defaultValue = "Hello") String content,
                                  @RequestParam(defaultValue = "Guest") String conversation)
    {

        Conversation currentConversation;

        if (conversationRepository.findOneByName(conversation) == null)
        {
            System.out.println("Conversations not found, creating : " + conversation);
            conversationRepository.save(new Conversation(conversation));
        }

        currentConversation = conversationRepository.findOneByName(conversation);

        TextMessage message = new TextMessage(user, content, currentConversation);

        messageRepository.save(message);

        return message;
    }

    @GetMapping("delete")
    public String deleteMessages()
    {
        messageRepository.deleteAll();
        conversationRepository.deleteAll();
        return "All deleted";
    }


}
