package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemRequestMapperTest {

    @Autowired
    private ItemRequestMapper itemRequestMapper;

    @Test
    void toItemRequestDto() {
        ItemRequest itemRequest = new ItemRequest(1L, "description", 1L, LocalDateTime.now());

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest);

        assertEquals(itemRequest.getId(), itemRequestDto.getId());
        assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());

    }

    @Test
    void toItemRequest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "description",
                LocalDateTime.now(), new ArrayList<>());

        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto);

        assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
    }
}