package com.gadator.gadator.service;

import com.gadator.gadator.DTO.ConversationDTO;
import com.gadator.gadator.DTO.TextMessageDTO;
import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.exception.InvalidUserException;
import com.gadator.gadator.exception.NameExistsException;
import com.gadator.gadator.repository.ConversationRepository;
import com.gadator.gadator.repository.TextMessageRepository;
import com.gadator.gadator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
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
