package com.gadator.messages.services;

import com.gadator.messages.DTO.TextMessageDTO;
import com.gadator.messages.entities.TextMessage;
import com.gadator.users.exceptions.InvalidUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

public interface MessagesService {

    Page<TextMessageDTO> findAllMessagesByConversationName(String conversationName, Pageable pageable);

    TextMessage saveNewMessage(@Valid TextMessageDTO textMessageDTO) throws InvalidUserException;

    List<TextMessageDTO> findAllMessagesByAuthor(String author);

    void deleteAllMessagesByAuthor(String author);

    void deleteAllMessagesByConversation(String conversationName);

}
