package ru.shareit.UT.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.shareit.comment.CommentDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Comment dto test
 */

@JsonTest
class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    /**
     * Test comment dto
     */

    @Test
    void testCommentDto() throws Exception {
        CommentDto commentDto = new CommentDto("comment");

        JsonContent<CommentDto> result = json.write(commentDto);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("comment");
    }
}