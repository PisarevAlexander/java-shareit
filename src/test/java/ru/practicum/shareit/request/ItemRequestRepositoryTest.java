package ru.practicum.shareit.request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import ru.practicum.shareit.OffsetBasedPageRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRequestRepositoryTest {

    private ItemRequest itemRequest;
    private User user;

    @Autowired
    private ItemRequestRepository repository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void addRequest() {
        user = new User(1L, "test", "test@gmail.com");
        user = userRepository.save(user);
        itemRequest = new ItemRequest(1L, "description", user.getId(), LocalDateTime.now());
        itemRequest = repository.save(itemRequest);
    }

    @AfterEach
    public void delete() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllByUserOrderByCreatedDesc() {
        List<ItemRequest> itemsRequest = repository.findAllByUserOrderByCreatedDesc(itemRequest.getUser());

        assertEquals(List.of(itemRequest), itemsRequest);
    }

    @Test
    void findById() {
        Optional<ItemRequest> actualItem = repository.findById(itemRequest.getId());

        assertTrue(actualItem.isPresent());
    }

    @Test
    void findAllByUserNot() {
        Page<ItemRequest> itemsRequest  = repository.
                findAllByUserNot(user.getId() + 1, new OffsetBasedPageRequest(0, 1));

        assertEquals(List.of(itemRequest), itemsRequest.getContent());
    }
}