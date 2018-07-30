package com.gadator.conversations.services;

import com.gadator.users.exceptions.NameExistsException;
import com.gadator.conversations.DTO.ConversationDTO;
import com.gadator.conversations.entities.Conversation;
import com.gadator.conversations.exceptions.NoConversationException;

import java.util.List;

/**
 * Interface for getting conversations and messages
 *
 * @author Konriz
 */
public interface ConversationService {

    List<Conversation> findAll();

    Conversation findConversationByName(String name);

    Conversation createNewConversation(ConversationDTO conversationDTO) throws NameExistsException;

    void deleteConversation(String conversationName) throws NoConversationException;





}
