package ru.shareit.IT.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.shareit.OffsetBasedPageRequest;
import ru.shareit.item.Item;
import ru.shareit.item.ItemMapper;
import ru.shareit.item.ItemRepository;
import ru.shareit.request.ItemRequest;
import ru.shareit.request.ItemRequestDto;
import ru.shareit.request.ItemRequestRepository;
import ru.shareit.request.ItemRequestServiceImpl;
import ru.shareit.user.User;
import ru.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Item request service impl test
 */

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    public UserService userService;

    private ItemRequest itemRequest;
    private ItemRequestDto itemRequestDto;
    private User user;
    private Item item;
    LocalDateTime time;

    /**
     * Sets up
     */

    @BeforeEach
    public void setUp() {
        time = LocalDateTime.now();
        itemRequest = new ItemRequest(null, "description", 1L, time);
        itemRequestDto = new ItemRequestDto(null, "description",
                time, null);
        user = new User(1L, "test", "test@gmail.com");
        item = new Item(null, "test", "description", true, 1L, 1L);
    }

    /**
     * Create test
     */

    @Test
    void create() {
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.save(itemRequest))
                .thenReturn(itemRequest);

        ItemRequest actualRequest = itemRequestService.create(itemRequestDto, 1L, time);

        assertEquals(itemRequest.getDescription(), actualRequest.getDescription());
        assertEquals(itemRequest.getUser(), actualRequest.getUser());
    }

    /**
     * Gets all test
     */

    @Test
    void getAll() {
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserOrderByCreatedDesc(1L))
                .thenReturn(List.of(itemRequest));
        itemRequestDto.setItems(new ArrayList<>());

        List<ItemRequestDto> requests = itemRequestService.getAll(1L);

        assertEquals(List.of(itemRequestDto), requests);
    }

    /**
     * Gets all with size test
     */

    @Test
    void getAllWithSize() {
        Pageable pageable = new OffsetBasedPageRequest(0, 1, Sort.by("created").descending());
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserNot(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(itemRequest)));
        itemRequestDto.setItems(new ArrayList<>());

        List<ItemRequestDto> requests = itemRequestService
                .getAll(new OffsetBasedPageRequest(1, 1, Sort.by("created").descending()), 1L);

        assertEquals(List.of(itemRequestDto), requests);
    }

    /**
     * Gets all with size when items request empty test
     */

    @Test
    void getAllWithSize_WhenItemsRequestEmpty() {
        Pageable pageable = new OffsetBasedPageRequest(0, 1, Sort.by("created").descending());
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserNot(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        List<ItemRequestDto> requests = itemRequestService
                .getAll(new OffsetBasedPageRequest(1, 1, Sort.by("created").descending()), 1L);

        assertEquals(new ArrayList<>(), requests);
    }

    /**
     * Gets all with size when items empty test
     */

    @Test
    void getAllWithSize_WhenItemsEmpty() {
        Pageable pageable = new OffsetBasedPageRequest(0, 1, Sort.by("created").descending());
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserNot(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(itemRequest)));
        when(itemRepository.findAllByRequestIdIn(anyList()))
                .thenReturn(List.of(item));
        itemRequestDto.setItems(new ArrayList<>());

        List<ItemRequestDto> requests = itemRequestService
                .getAll(new OffsetBasedPageRequest(1, 1, Sort.by("created").descending()), 1L);

        assertEquals(List.of(itemRequestDto), requests);
    }

    /**
     * Gets by id test
     */

    @Test
    void getById() {
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findById(1L))
                .thenReturn(Optional.of(itemRequest));
        when(itemRepository.findAllByRequestId(1L))
                .thenReturn(List.of(item));
        itemRequestDto.setItems(List.of(ItemMapper.toItemForRequestDto(item)));

        ItemRequestDto requestDto = itemRequestService.getById(1L, 1L);

        assertEquals(itemRequestDto, requestDto);
    }
}