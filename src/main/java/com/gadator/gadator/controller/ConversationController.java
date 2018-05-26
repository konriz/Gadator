package com.gadator.gadator.controller;

import com.gadator.gadator.DTO.ConversationDTO;
import com.gadator.gadator.DTO.MessageDTO;
import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.exception.NameExistsException;
import com.gadator.gadator.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
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

    @GetMapping("/id/{name}")
    public ModelAndView getMessages(@PathVariable("name") String conversationName)
    {
        Conversation conversation = conversationService.findConversationByName(conversationName);

        if(conversation != null)
        {
            ModelAndView mav = new ModelAndView("conversations/conversation");
            mav.addObject("conversation", conversation);

            List<TextMessage> messages = conversationService.findAllMessagesByConversationName(conversationName);
            mav.addObject("messages", messages);

            MessageDTO message = new MessageDTO();
            mav.addObject("message", message);

            return mav;
        }

        return new ModelAndView("conversations/id/null");
    }

    @PostMapping("/id/{name}")
    public ModelAndView sendMessage(@PathVariable("name") String conversationName,
                                    @ModelAttribute("messageContent") MessageDTO messageDTO,
                                    Principal principal,
                                    BindingResult bindingResult,
                                    Model model)
    {
        // TODO add validators
        messageDTO.setConversationName(conversationName);
        messageDTO.setAuthor(principal.getName());

        conversationService.saveNewMessage(messageDTO);

        return new ModelAndView("redirect:/conversations/id/" + conversationName);
    }

    @GetMapping("/deleteConversation")
    public String deleteConversation(@RequestParam("name") String conversationName)
    {
        conversationService.deleteConversation(conversationName);
        return "Done!";
    }

    @GetMapping("/add")
    public ModelAndView addConversation()
    {
        ConversationDTO conversationDTO = new ConversationDTO();

        ModelAndView mav = new ModelAndView("conversations/create");
        mav.addObject("conversation", conversationDTO);

        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addConversation(@ModelAttribute("conversation") @Valid ConversationDTO conversationDTO,
                                        BindingResult result, WebRequest request)
    {
        Conversation conversation = new Conversation();

        if(!result.hasErrors())
        {
            conversation = createConversation(conversationDTO, result);
        }
        if(conversation == null)
        {
            result.rejectValue("name", "message.regError");
        }
        if(result.hasErrors())
        {
            return new ModelAndView("conversations/add", "conversation", conversationDTO);
        }

        return new ModelAndView("redirect:/conversations/id/" + conversation.getName());
    }

    private Conversation createConversation(ConversationDTO conversationDTO, BindingResult result)
    {
        Conversation conversation = null;
        try
        {
            conversation = conversationService.createNewConversation(conversationDTO);
        } catch (NameExistsException e)
        {
            return null;
        }

        return conversation;
    }

}
