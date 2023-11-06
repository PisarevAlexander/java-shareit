package ru.shareit.UT.request;

import org.junit.jupiter.api.Test;
import ru.shareit.request.ItemRequest;
import ru.shareit.request.ItemRequestDto;
import ru.shareit.request.ItemRequestMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Item request mapper test
 */

class ItemRequestMapperTest {

    /**
     * To item request dto test
     */

    @Test
    void toItemRequestDto() {
        ItemRequest itemRequest = new ItemRequest(1L, "description", 1L, LocalDateTime.now());

        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);

        assertEquals(itemRequest.getId(), itemRequestDto.getId());
        assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());

    }

    /**
     * To item request test
     */

    @Test
    void toItemRequest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "description",
                LocalDateTime.now(), new ArrayList<>());

        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);

        assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
    }
}