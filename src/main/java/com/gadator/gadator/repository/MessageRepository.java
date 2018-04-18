package com.gadator.gadator.repository;

import com.gadator.gadator.entity.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<TextMessage, Integer> {
}
