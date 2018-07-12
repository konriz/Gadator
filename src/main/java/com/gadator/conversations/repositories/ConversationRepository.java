package com.gadator.conversations.repositories;

import com.gadator.conversations.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer>{

    public Conversation findOneByName(String name);
}
