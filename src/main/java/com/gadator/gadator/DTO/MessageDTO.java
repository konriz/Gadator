package com.gadator.gadator.DTO;

import com.gadator.gadator.entity.TextMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MessageDTO {

//    @NotNull
//    @NotEmpty
    private String author;

//    @NotNull
//    @NotEmpty
    private String content;

//    @NotNull
//    @NotEmpty
    private String conversationName;

    @Override
    public String toString()
    {
        return author + " : " + content + " @ " + conversationName;
    }

    public MessageDTO(){}

    public MessageDTO(TextMessage message)
    {
        this.setAuthor(message.getUser().getName());
        this.setContent(message.getContent());
        this.setConversationName(message.getConversation().getName());
    }

}
