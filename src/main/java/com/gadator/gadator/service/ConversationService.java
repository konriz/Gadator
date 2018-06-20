package com.gadator.gadator.service;

import com.gadator.gadator.DTO.ConversationDTO;
import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.exception.NameExistsException;

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

    void deleteConversation(String conversationName);





}
