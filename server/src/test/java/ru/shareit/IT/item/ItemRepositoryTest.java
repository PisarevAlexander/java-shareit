package ru.shareit.IT.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.shareit.OffsetBasedPageRequest;
import ru.shareit.item.Item;
import ru.shareit.item.ItemRepository;
import ru.shareit.request.ItemRequest;
import ru.shareit.request.ItemRequestRepository;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Item repository test
 */

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository requestRepository;

    private Item item;

    /**
     * Add user
     */

    @BeforeEach
    public void addUser() {
        User user1 = new User(1L, "test", "test@gmail.com");
        User user2 = new User(2L, "test1", "test1@gmail.com");
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        ItemRequest itemRequest = new ItemRequest(1L, "request", user2.getId(), LocalDateTime.now());
        itemRequest = requestRepository.save(itemRequest);
        item = new Item(1L, "item", "description", true, user1.getId(), itemRequest.getId());
        item = itemRepository.save(item);
    }

    /**
     * Delete repository
     */

    @AfterEach
    public void delete() {
        requestRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Find item by id test
     */

    @Test
    void findItemById() {
        Optional<Item> testItem = itemRepository.findItemById(item.getId());

        assertTrue(testItem.isPresent());
    }

    /**
     * Find all by name containing ignore case or description containing ignore case and available true test
     */

    @Test
    void findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue() {
        List<Item> items = itemRepository
                .findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(item.getName(),
                        item.getName(), new OffsetBasedPageRequest(0, 1));

        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
    }

    /**
     * Find all by request id test
     */

    @Test
    void findAllByRequestId() {
        List<Item> items = itemRepository.findAllByRequestId(item.getRequestId());

        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
    }

    /**
     * Find all by request id in test
     */

    @Test
    void findAllByRequestIdIn() {
        List<Item> items = itemRepository.findAllByRequestIdIn(List.of(item.getRequestId()));

        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
    }
}