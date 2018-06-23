package com.gadator.service;

import com.gadator.exception.NameExistsException;
import com.gadator.DTO.ConversationDTO;
import com.gadator.entity.Conversation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for getting conversations and messages
 *
 * @author Konriz
 */
@Service("conversationService")
public interface ConversationService {

    List<Conversation> findAll();

    Conversation findConversationByName(String name);

    Conversation createNewConversation(ConversationDTO conversationDTO) throws NameExistsException;

    void deleteConversation(String conversationName);





}
