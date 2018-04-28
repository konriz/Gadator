package com.gadator.gadator.service;

import com.gadator.gadator.DTO.ConversationDTO;
import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.exception.NameExistsException;
import com.gadator.gadator.repository.ConversationRepository;
import com.gadator.gadator.repository.TextMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private TextMessageRepository textMessageRepository;

    public List<Conversation> findAll()
    {
        return this.conversationRepository.findAll();
    }

    public Conversation findConversationByName(String name)
    {
        return this.conversationRepository.findOneByName(name);
    }

    public Conversation createNewConversation(ConversationDTO conversationDTO) throws NameExistsException
    {
        if(nameExists(conversationDTO.getName()))
        {
            throw new NameExistsException("There is a conversation named " + conversationDTO.getName());
        }

        Conversation conversation = new Conversation();
        conversation.setName(conversationDTO.getName());

        return conversationRepository.save(conversation);
    }

    private boolean nameExists(String name)
    {
        Conversation conversation = conversationRepository.findOneByName(name);
        if (conversation != null)
        {
            return true;
        }
        return false;
    }

    public void delete(String conversationName)
    {
        Conversation conversation = findConversationByName(conversationName);
        textMessageRepository.deleteAll(textMessageRepository.findAllByConversation(conversation));
        conversationRepository.delete(conversation);
    }

    public List<TextMessage> findAllMessagesByConversationName(String conversationName) throws NullPointerException
    {
        Conversation conversation = this.findConversationByName(conversationName);
        return this.textMessageRepository.findAllByConversation(conversation);
    }

    public void save(TextMessage message)
    {
        this.textMessageRepository.save(message);
    }



}
