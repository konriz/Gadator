package com.gadator.service;

import com.gadator.DTO.ConversationDTO;
import com.gadator.entity.Conversation;
import com.gadator.entity.TextMessage;
import com.gadator.entity.User;
import com.gadator.exception.NameExistsException;
import com.gadator.repository.ConversationRepository;
import com.gadator.repository.TextMessageRepository;
import com.gadator.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ConversationServiceImplTest {

    @TestConfiguration
    static class ConversationServiceImplTestConfiguration
    {
        @Bean
        public ConversationService conversationService()
        {
            return new ConversationServiceImpl();
        }
    }

    @Autowired
    private ConversationService conversationService;

    @MockBean
    private ConversationRepository conversationRepository;

    @MockBean
    private TextMessageRepository textMessageRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp()
    {
        Conversation existingConversation = new Conversation();
        existingConversation.setName("TestConversation");

        Mockito.when(conversationRepository.findOneByName(existingConversation.getName()))
                .thenReturn(existingConversation);

        Conversation uniqueConversation = new Conversation();
        uniqueConversation.setName("UniqueName");

        Mockito.when(conversationRepository.save(any(Conversation.class)))
                .thenReturn(uniqueConversation);

    }

    @Test
    public void whenValidConversationName_thenConversationShouldBeFound()
    {
        String name = "TestConversation";
        Conversation foundConversation = conversationService.findConversationByName(name);

        assertEquals(foundConversation.getName(), name);

    }

    @Test
    public void givenUniqueConversationName_whenCreateNewConversation_thenConversationsIsCreated() throws NameExistsException
    {
        String name = "UniqueName";
        ConversationDTO inputConversation = new ConversationDTO();
        inputConversation.setName(name);

        Conversation savedConversation = conversationService.createNewConversation(inputConversation);

        assertEquals(savedConversation.getName(), name);

    }

    @Test(expected = NameExistsException.class)
    public void givenExistingConversationName_whenCreateNewConversation_thenNameExistExceptionIsThrown() throws NameExistsException
    {
        String name = "TestConversation";
        ConversationDTO inputConversation = new ConversationDTO();
        inputConversation.setName(name);

        conversationService.createNewConversation(inputConversation);

    }

}
