package com.gadator.messages.repositories;

import com.gadator.messages.DTO.TextMessageDTO;
import com.gadator.conversations.entities.Conversation;
import com.gadator.messages.entities.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TextMessageRepository extends JpaRepository<TextMessage, Integer>{


    List<TextMessage> findAllByConversation(Conversation c);

    @Query(
            "Select new com.gadator.messages.DTO.TextMessageDTO(t) " +
                    "from TextMessage t " +
                    "where t.conversation.name = :name " +
                    "order by t.sentDate asc"
    )
    Page<TextMessageDTO> findAllDtoByConversation(@Param("name")String name, Pageable pageable);

    @Query(
            "Select t from TextMessage t " +
                    "where t.user.name = :author"
    )
    List<TextMessage> findAllByAuthor(@Param("author") String author);

    @Query(
            "Select new com.gadator.messages.DTO.TextMessageDTO(t)" +
                    " from TextMessage t " +
                    "where t.user.name = :author"
    )
    List<TextMessageDTO> findAllDTOByAuthor(@Param("author") String author);

    @Query(
            "Select new com.gadator.messages.DTO.TextMessageDTO(t) " +
                    "from TextMessage t " +
                    "order by t.sentDate asc"
    )
    Page<TextMessageDTO> findAllDto(Pageable pageable);





}
