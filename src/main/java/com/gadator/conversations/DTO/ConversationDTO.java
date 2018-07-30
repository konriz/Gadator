package com.gadator.conversations.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ConversationDTO {

    @NotEmpty
    @NotNull
    private String name;
}
