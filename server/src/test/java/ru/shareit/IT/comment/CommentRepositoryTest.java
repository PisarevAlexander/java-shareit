package ru.shareit.IT.comment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.shareit.comment.Comment;
import ru.shareit.comment.CommentRepository;
import ru.shareit.item.Item;
import ru.shareit.item.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Comment repository test.
 */

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Comment comment;
    private Item item;

    /**
     * Add user test
     */

    @BeforeEach
    public void addUser() {
        item = new Item(1L, "item", "description", true, null, null);
        item = itemRepository.save(item);
        comment = new Comment(1L, "comment", item, "vasia", LocalDateTime.now());
        comment = commentRepository.save(comment);
    }

    /**
     * Delete test
     */

    @AfterEach
    public void delete() {
        commentRepository.deleteAll();
        itemRepository.deleteAll();
    }

    /**
     * Find all by item id test
     */

    @Test
    void findAllByItem_Id() {
        List<Comment> comments = commentRepository.findAllByItem_Id(item.getId());

        assertEquals(1, comments.size());
        assertEquals(comment, comments.get(0));
    }
}