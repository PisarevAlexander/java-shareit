package ru.shareit.comment;

/**
 * The type Comment mapper
 */

public class CommentMapper {

    /**
     * Comment dto to comment
     * @param commentDto the comment dto
     * @return the comment
     */

    public static Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        return comment;
    }
}