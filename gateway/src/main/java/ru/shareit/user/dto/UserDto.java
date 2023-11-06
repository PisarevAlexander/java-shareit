package ru.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The type User dto
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "login can't be blank")
    private String name;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "email is not valid")
    @NotBlank(message = "email can't be blank")
    private String email;
}