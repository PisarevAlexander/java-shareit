package ru.practicum.shareit.comment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Comment comment;
    private Item item;

    @BeforeEach
    public void addUser() {
        item = new Item(1L, "item", "description", true, null, null);
        item = itemRepository.save(item);
        comment = new Comment(1L, "comment", item, "vasia", LocalDateTime.now());
        comment = commentRepository.save(comment);
    }

    @AfterEach
    public void delete() {
        commentRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void findAllByItem_Id() {
        List<Comment> comments = commentRepository.findAllByItem_Id(item.getId());

        assertEquals(1, comments.size());
        assertEquals(comment, comments.get(0));
    }
}