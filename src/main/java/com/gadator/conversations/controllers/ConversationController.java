package com.gadator.conversations.controllers;

import com.gadator.conversations.DTO.ConversationDTO;
import com.gadator.messages.DTO.TextMessageDTO;
import com.gadator.conversations.entities.Conversation;
import com.gadator.users.exceptions.InvalidUserException;
import com.gadator.users.exceptions.NameExistsException;
import com.gadator.conversations.exceptions.NoConversationException;
import com.gadator.conversations.services.ConversationService;
import com.gadator.messages.services.MessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessagesService messagesService;

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
    public ModelAndView getMessages(@PathVariable("name") String conversationName, Pageable pageable)
    {
        Conversation conversation = conversationService.findConversationByName(conversationName);

        if(conversation != null)
        {
            ModelAndView mav = new ModelAndView("conversations/conversation");
            mav.addObject("conversation", conversation);

            // messages list
            Page<TextMessageDTO> messages = messagesService.findAllMessagesByConversationName(
                    conversationName, pageable);
            mav.addObject("messages", messages);

            // new message
            TextMessageDTO message = new TextMessageDTO();
            mav.addObject("message", message);

            return mav;
        }

        return new ModelAndView("conversations/null");
    }

    @PostMapping("/id/{name}")
    public ModelAndView sendMessage(@PathVariable("name") String conversationName,
                                    @ModelAttribute("message") TextMessageDTO textMessageDTO,
                                    Principal principal)
    {

        textMessageDTO.setAuthor(principal.getName());
        textMessageDTO.setConversationName(conversationName);

        try{
            messagesService.saveNewMessage(textMessageDTO);
        }
        catch (InvalidUserException e)
        {
            return new ModelAndView("redirect:/conversations/id/" + conversationName);
        }

        return new ModelAndView("redirect:/conversations/id/" + conversationName + "/last");
    }

    @GetMapping("/id/{name}/last")
    public ModelAndView getLastPage(@PathVariable("name") String conversationName, Pageable pageable)
    {

        Page<TextMessageDTO> firstPage = messagesService.findAllMessagesByConversationName(conversationName, pageable);

        return getMessages(conversationName, PageRequest.of(firstPage.getTotalPages() - 1, pageable.getPageSize()));
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    public ModelAndView deleteConversation(@RequestParam("name") String conversationName)
    {
        Conversation conversation = conversationService.findConversationByName(conversationName);
        if(conversation == null)
        {
            return new ModelAndView("conversations/null");
        }

        ModelAndView mav = new ModelAndView("conversations/delete");
        mav.addObject("conversation", conversation);
        return mav;
    }

    @PostMapping("/delete")
    public ModelAndView deleteConfirm(@RequestParam("name") String conversationName)
    {
        try {
            conversationService.deleteConversation(conversationName);
        }
        catch (NoConversationException e)
        {
            log.error(e.getMessage());
        }
        return new ModelAndView("redirect:/conversations");
    }

    @GetMapping("/create")
    public ModelAndView addConversation()
    {
        ConversationDTO conversationDTO = new ConversationDTO();

        ModelAndView mav = new ModelAndView("conversations/create");
        mav.addObject("conversation", conversationDTO);

        return mav;
    }

    @PostMapping("/create")
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
            return new ModelAndView("redirect:/conversations/create");
        }

        return new ModelAndView("redirect:/conversations/id/" + conversation.getName());
    }

    private Conversation createConversation(ConversationDTO conversationDTO, BindingResult result) {
        Conversation conversation = null;
        try {
            conversation = conversationService.createNewConversation(conversationDTO);
        } catch (NameExistsException e) {
            log.info(e.getMessage());
        }
        return conversation;
    }

}
