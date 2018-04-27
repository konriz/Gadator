package com.gadator.gadator.controller;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.repository.ConversationRepository;
import com.gadator.gadator.repository.TextMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private ConversationRepository conversationRepository;



    @GetMapping(value = {"", "/", "/list"})
    public ModelAndView getConversations()
    {
        ModelAndView mav = new ModelAndView("conversations/list");

        List<String> conversationNames = new ArrayList<>();
        conversationRepository.findAll().forEach(p -> conversationNames.add(p.getName()));

        mav.addObject("list", conversationNames);

        return mav;
    }

    @GetMapping("/{cName}")
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

    @GetMapping("/add/{cName}")
    public String addConversation(@PathVariable("cName") String conversationName)
    {
        conversationRepository.save(new Conversation(conversationName));
        return "Done!";
    }

}
