package com.gadator.gadator.service;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
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

    public void save(Conversation conversation)
    {
        this.conversationRepository.save(conversation);
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
