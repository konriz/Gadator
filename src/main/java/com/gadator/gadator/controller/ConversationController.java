package com.gadator.gadator.controller;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping(value = {"", "/"})
    public ModelAndView getConversations()
    {
        ModelAndView mav = new ModelAndView("conversations/list");

        List<String> conversationNames = new ArrayList<>();
        conversationService.findAll().forEach(p -> conversationNames.add(p.getName()));

        mav.addObject("list", conversationNames);

        return mav;
    }

    @GetMapping("/{name}")
    public ModelAndView getMessages(@PathVariable("name") String conversationName)
    {
        Conversation conversation = conversationService.findConversationByName(conversationName);

        if(conversation != null)
        {
            ModelAndView mav = new ModelAndView("conversations/conversation");
            List<TextMessage> messages = conversationService.findAllMessagesByConversationName(conversationName);
            mav.addObject("conversation", conversationName);
            mav.addObject("messages", messages);
            return mav;
        }

        return new ModelAndView("conversations/null");
    }

    @GetMapping("/delete")
    public String deleteConversation(@RequestParam("name") String conversationName)
    {
        conversationService.delete(conversationName);
        return "Done!";
    }

    @GetMapping("/add")
    public String addConversation(@RequestParam("name") String conversationName)
    {
        conversationService.save(new Conversation(conversationName));
        return "Done!";
    }

}
