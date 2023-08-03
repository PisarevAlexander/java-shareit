package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.OffsetBasedPageRequest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setUp() {
        time = LocalDateTime.now();
        itemRequest = new ItemRequest(null, "description", 1L, time);
        itemRequestDto = new ItemRequestDto(null, "description",
                time, null);
        user = new User(1L, "test", "test@gmail.com");
        item = new Item(null, "test", "description", true, 1L, 1L);
    }

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

    @Test
    void getAllWithSize() {
        Pageable pageable = new OffsetBasedPageRequest(0, 1, Sort.by("created").descending());
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserNot(1L, pageable))
                .thenReturn(new PageImpl<>(List.of(itemRequest)));
        itemRequestDto.setItems(new ArrayList<>());

        List<ItemRequestDto> requests = itemRequestService.getAllWithSize(0, 1, 1L);

        assertEquals(List.of(itemRequestDto), requests);
    }

    @Test
    void getAllWithSize_WhenItemsRequestEmpty() {
        Pageable pageable = new OffsetBasedPageRequest(0, 1, Sort.by("created").descending());
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserNot(1L, pageable))
                .thenReturn(new PageImpl<>(List.of()));

        List<ItemRequestDto> requests = itemRequestService.getAllWithSize(0, 1, 1L);

        assertEquals(new ArrayList<>(), requests);
    }

    @Test
    void getAllWithSize_WhenItemsEmpty() {
        Pageable pageable = new OffsetBasedPageRequest(0, 1, Sort.by("created").descending());
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRequestRepository.findAllByUserNot(1L, pageable))
                .thenReturn(new PageImpl<>(List.of(itemRequest)));
        when(itemRepository.findAllByRequestIdIn(anyList()))
                .thenReturn(List.of(item));
        itemRequestDto.setItems(new ArrayList<>());

        List<ItemRequestDto> requests = itemRequestService.getAllWithSize(0, 1, 1L);

        assertEquals(List.of(itemRequestDto), requests);
    }

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