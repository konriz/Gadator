package com.gadator.service;

import com.gadator.DTO.TextMessageDTO;
import com.gadator.entity.TextMessage;
import com.gadator.exception.InvalidUserException;
import com.gadator.repository.TextMessageRepository;
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
public class MessagesServiceImpl implements MessagesService {


    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

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

        message.setUser(userService.findOneByName(textMessageDTO.getAuthor()));
        message.setConversation(conversationService.findConversationByName(textMessageDTO.getConversationName()));
        message.setContent(textMessageDTO.getContent());
        message.setSentDate(new Date());

        log.info("Message saved : " + message.toString());

        return this.textMessageRepository.save(message);
    }

    private boolean userExists(String userName)
    {
        if(userService.findOneByName(userName) == null)
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

    @Override
    public void deleteAllMessagesByConversation(String conversationName)
    {
        textMessageRepository
                .deleteInBatch(
                        textMessageRepository
                                .findAllByConversation(conversationService
                                        .findConversationByName(conversationName)));
    }
}
