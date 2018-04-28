package com.gadator.gadator.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MessageDTO {

    @NotNull
    @NotEmpty
    private String authorName;

    @NotNull
    @NotEmpty
    private String content;

    @NotNull
    @NotEmpty
    private String conversationName;

}
