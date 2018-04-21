package com.gadator.gadator.controller;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.repository.ConversationRepository;
import com.gadator.gadator.repository.TextMessageRepository;
import com.gadator.gadator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ConversationController {

    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<String> getConversations()
    {
        List<String> conversationNames = new ArrayList<>();
        conversationRepository.findAll().forEach(p -> conversationNames.add(p.getName()));

        return conversationNames;
    }

    @GetMapping("{cName}")
    public List<TextMessage> getMessages(@PathVariable("cName") String conversationName)
    {
        try
        {
            return textMessageRepository.findAllByConversation(conversationRepository.findOneByName(conversationName));
        }
        catch (NullPointerException e)
        {
            return new ArrayList<>();
        }
    }

    @GetMapping("user")
    public User createUser(@RequestParam String name)
    {
        userRepository.save(new User(name));
        return userRepository.findOneByName(name);
    }

    @GetMapping("users")
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("add")
    public TextMessage addMessage(@RequestParam(defaultValue = "guest") String userName,
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


        User user;
        if (userRepository.findOneByName(userName) == null)
        {
            createUser(userName);
        }
        user = userRepository.findOneByName(userName);

        TextMessage message = new TextMessage(user, content, currentConversation);

        textMessageRepository.save(message);

        return message;
    }

    @GetMapping("deleteAll")
    public String deleteAll()
    {
        textMessageRepository.deleteAll();
        conversationRepository.deleteAll();
        userRepository.deleteAll();
        return "All deleted";
    }

}
