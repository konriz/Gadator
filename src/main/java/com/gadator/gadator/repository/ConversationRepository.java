package com.gadator.gadator.repository;

import com.gadator.gadator.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer>{

    public Conversation findOneByName(String name);
}
