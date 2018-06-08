package com.gadator.gadator.repository;

import com.gadator.gadator.DTO.TextMessageDTO;
import com.gadator.gadator.entity.Conversation;
import com.gadator.gadator.entity.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TextMessageRepository extends JpaRepository<TextMessage, Integer>{


    List<TextMessage> findAllByConversation(Conversation c);

    @Query(
            "Select new com.gadator.gadator.DTO.TextMessageDTO(t) " +
                    "from TextMessage t " +
                    "order by t.sentDate asc"
    )
    Page<TextMessageDTO> findAllDto(Pageable pageable);

    @Query(
            "Select new com.gadator.gadator.DTO.TextMessageDTO(t) " +
                    "from TextMessage t " +
                    "where t.conversation.name = :name " +
                    "order by t.sentDate asc"
    )
    Page<TextMessageDTO> findAllDtoByConversation(@Param("name")String name, Pageable pageable);

}
