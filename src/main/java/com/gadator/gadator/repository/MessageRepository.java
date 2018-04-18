package com.gadator.gadator.repository;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<TextMessage, Integer> {

    public List<TextMessage> findAllByConversation(Conversation conversation);
}
