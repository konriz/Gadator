package com.gadator.repository;

import com.gadator.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("conversationRepository")
public interface ConversationRepository extends JpaRepository<Conversation, Integer>{

    public Conversation findOneByName(String name);
}
