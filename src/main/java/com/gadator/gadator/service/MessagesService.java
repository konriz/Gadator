package com.gadator.gadator.service;

import com.gadator.gadator.DTO.TextMessageDTO;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.exception.InvalidUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

public interface MessagesService {

    Page<TextMessageDTO> findAllMessagesByConversationName(String conversationName, Pageable pageable);

    TextMessage saveNewMessage(@Valid TextMessageDTO textMessageDTO) throws InvalidUserException;


}
