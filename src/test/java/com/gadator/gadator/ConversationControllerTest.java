package com.gadator.gadator;

import com.gadator.gadator.DTO.ConversationDTO;
import com.gadator.gadator.DTO.MessageDTO;
import com.gadator.gadator.controller.ConversationController;
import com.gadator.gadator.entity.TextMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversationControllerTest {

    @Autowired
    private ConversationController conversationController;

//    @Before
//    public void createConversation()
//    {
//        ConversationDTO testConversation = new ConversationDTO();
//        testConversation.setName("hello");
//
//    }

    @Test
    public void createMessage() throws Exception
    {
        MessageDTO testMessage = new MessageDTO();
        testMessage.setAuthor("konriz");
        testMessage.setConversationName("hello");
        testMessage.setContent("Hello world");
    }
}
