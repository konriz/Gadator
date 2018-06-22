package com.gadator.gadator.service;

import com.gadator.gadator.DTO.TextMessageDTO;
import com.gadator.gadator.entity.TextMessage;
import com.gadator.gadator.exception.InvalidUserException;
import com.gadator.gadator.repository.ConversationRepository;
import com.gadator.gadator.repository.TextMessageRepository;
import com.gadator.gadator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class MessagesServiceImpl implements MessagesService {


    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Page<TextMessageDTO> findAllMessagesByConversationName(String conversationName, Pageable pageable) {
        return this.textMessageRepository.findAllDtoByConversation(conversationName, pageable);
    }

    @Override
    public TextMessage saveNewMessage(@Valid TextMessageDTO textMessageDTO) throws InvalidUserException {
        TextMessage message = new TextMessage();
        if (!userExists(textMessageDTO.getAuthor()))
        {
            throw new InvalidUserException("There is no user named " + textMessageDTO.getAuthor());
        }

        message.setUser(userRepository.findOneByName(textMessageDTO.getAuthor()));
        message.setConversation(conversationRepository.findOneByName(textMessageDTO.getConversationName()));
        message.setContent(textMessageDTO.getContent());
        message.setSentDate(new Date());

        log.info("Message saved : " + message.toString());

        return this.textMessageRepository.save(message);
    }

    private boolean userExists(String userName)
    {
        if(userRepository.findOneByName(userName) == null)
        {
            return false;
        }
        return true;
    }

    @Override
    public List<TextMessageDTO> findAllMessagesByAuthor(String author)
    {
        return textMessageRepository.findAllDTOByAuthor(author);
    }

    @Override
    public void deleteAllMessagesByAuthor(String author)
    {

        textMessageRepository.deleteInBatch(textMessageRepository.findAllByAuthor(author));
    }
}
