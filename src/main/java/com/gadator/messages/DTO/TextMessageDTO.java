package com.gadator.messages.DTO;

import com.gadator.messages.entities.TextMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class TextMessageDTO {

    @NotNull
    @NotEmpty
    private String author;

//    @NotNull
//    @NotEmpty
    private String content;

    @NotNull
    @NotEmpty
    private String conversationName;

    private Date sentDate;

    @Override
    public String toString()
    {
        return author + " : " + content + " @ " + conversationName;
    }

    public TextMessageDTO(){}

    public TextMessageDTO(TextMessage message)
    {
        this.setAuthor(message.getUser().getName());
        this.setContent(message.getContent());
        this.setConversationName(message.getConversation().getName());
        this.setSentDate(message.getSentDate());
    }

}
