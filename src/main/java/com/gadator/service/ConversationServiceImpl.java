package com.gadator.service;

import com.gadator.DTO.ConversationDTO;
import com.gadator.entity.Conversation;
import com.gadator.exception.NameExistsException;
import com.gadator.repository.ConversationRepository;
import com.gadator.repository.TextMessageRepository;
import com.gadator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Conversation> findAll()
    {
        return this.conversationRepository.findAll();
    }

    @Override
    public Conversation findConversationByName(String name)
    {
        return this.conversationRepository.findOneByName(name);
    }

    @Override
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

    public void deleteConversation(String conversationName)
    {
        Conversation conversation = findConversationByName(conversationName);
        textMessageRepository.deleteAll(textMessageRepository.findAllByConversation(conversation));
        conversationRepository.delete(conversation);
    }

}
