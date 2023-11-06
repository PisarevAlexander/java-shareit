package ru.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Comment dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private String text;
}