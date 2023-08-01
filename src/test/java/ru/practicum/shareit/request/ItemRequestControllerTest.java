package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {

    @Mock
    private ItemRequestService itemRequestService;

    @InjectMocks
    private ItemRequestController itemRequestController;

    private ItemRequest itemRequest;
    private ItemRequestDto itemRequestDto;
    LocalDateTime time;

    @BeforeEach
    void setUp() {
        time = LocalDateTime.now();
        itemRequest = new ItemRequest(1L, "description", 1L, time);
        itemRequestDto = new ItemRequestDto(1L, "description",
                time, new ArrayList<>());
    }

    @Test
    void create() {
        when(itemRequestService.create(itemRequestDto, itemRequest.getUser(), LocalDateTime.now()))
                .thenReturn(itemRequest);

        ItemRequest actualItem = itemRequestController.create(itemRequestDto, itemRequest.getUser());

        assertEquals(itemRequest, actualItem);
    }

    @Test
    void findAllRequest() {
        when(itemRequestService.getAll(itemRequest.getUser()))
                .thenReturn(List.of(itemRequestDto));

        List<ItemRequestDto> requestDtos = itemRequestController.findAllRequest(itemRequest.getUser());

        assertEquals(List.of(itemRequestDto), requestDtos);
    }

    @Test
    void findRequest() {
        when(itemRequestService.getAllWithSize(1, 1, itemRequest.getUser()))
                .thenReturn(List.of(itemRequestDto));

        List<ItemRequestDto> requestDtos = itemRequestController.findRequest(1, 1, itemRequest.getUser());

        assertEquals(List.of(itemRequestDto), requestDtos);
    }

    @Test
    void findRequestById() {
        when(itemRequestService.getById(1L, itemRequest.getUser()))
                .thenReturn(itemRequestDto);

        ItemRequestDto actualRequest = itemRequestController.findRequestById(1L, itemRequest.getUser());

        assertEquals(itemRequestDto, actualRequest);
    }
}