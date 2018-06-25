package com.gadator.DTO;

import com.gadator.entity.User;
import com.gadator.validator.PasswordMatches;
import com.gadator.validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
public class UserDTO {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    public UserDTO(){}

    public UserDTO(User user)
    {
        setName(user.getName());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
    }

}
