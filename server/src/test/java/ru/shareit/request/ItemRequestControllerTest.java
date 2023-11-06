package ru.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {

    @Mock
    private ItemRequestService itemRequestService;

    @InjectMocks
    private ItemRequestController itemRequestController;

    private ItemRequest itemRequest;
    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {
        LocalDateTime time = LocalDateTime.now();
        itemRequest = new ItemRequest(1L, "description", 1L, time);
        itemRequestDto = new ItemRequestDto(1L, "description",
                time, new ArrayList<>());
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
        when(itemRequestService.getAll(any(Pageable.class), anyLong()))
                .thenReturn(List.of(itemRequestDto));

        List<ItemRequestDto> requestDtos = itemRequestController
                .findRequest(1, 1,1L);

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