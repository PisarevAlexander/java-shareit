package ru.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * The type Comment dto
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @NotBlank
    private String text;
}
