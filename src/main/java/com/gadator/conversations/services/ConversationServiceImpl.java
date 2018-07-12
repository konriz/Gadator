package com.gadator.conversations.services;

import com.gadator.conversations.DTO.ConversationDTO;
import com.gadator.conversations.entities.Conversation;
import com.gadator.users.exceptions.NameExistsException;
import com.gadator.conversations.exceptions.NoConversationException;
import com.gadator.conversations.repositories.ConversationRepository;
import com.gadator.messages.services.MessagesService;
import com.gadator.users.services.UserService;
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
    private MessagesService textMessageService;

    @Autowired
    private UserService userService;

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

    public void deleteConversation(String conversationName) throws NoConversationException
    {
        Conversation conversation = findConversationByName(conversationName);

        if(conversation == null)
        {
            throw new NoConversationException(conversationName);
        }

        textMessageService.deleteAllMessagesByConversation(conversationName);
        conversationRepository.delete(conversation);

    }

}
