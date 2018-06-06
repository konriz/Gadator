package com.gadator.gadator.repository;

import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TextMessageRepository extends JpaRepository<TextMessage, Integer>{

    List<TextMessage> findAllByConversation(Conversation c);



}
