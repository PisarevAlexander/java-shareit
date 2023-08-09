package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {


    @Test
    void toItemDtoWithNotNullRequest() {
        Item item = new Item(1L, "test", "description", true, 1L, 1L);

        ItemDto actualItemDto = ItemMapper.toItemDto(item);

        assertEquals(item.getId(), actualItemDto.getId());
        assertEquals(item.getName(), actualItemDto.getName());
        assertEquals(item.getDescription(), actualItemDto.getDescription());
        assertEquals(item.isAvailable(), actualItemDto.getAvailable());
        assertEquals(item.getRequestId(), actualItemDto.getRequestId());
    }

    @Test
    void toItemDtoWithNullRequest() {
        Item item = new Item(1L, "test", "description", true, 1L, null);

        ItemDto actualItemDto = ItemMapper.toItemDto(item);

        assertEquals(item.getId(), actualItemDto.getId());
        assertEquals(item.getName(), actualItemDto.getName());
        assertEquals(item.getDescription(), actualItemDto.getDescription());
        assertEquals(item.isAvailable(), actualItemDto.getAvailable());
        assertEquals(item.getRequestId(), actualItemDto.getRequestId());
    }

    @Test
    void toItem() {
        ItemDto itemDto = new ItemDto(1L, "test", "description", true, 1L,
                null, null, new ArrayList<>());

        Item actualItem = ItemMapper.toItem(itemDto);

        assertEquals(itemDto.getName(), actualItem.getName());
        assertEquals(itemDto.getDescription(), actualItem.getDescription());
        assertEquals(itemDto.getAvailable(), actualItem.isAvailable());
        assertEquals(itemDto.getRequestId(), actualItem.getRequestId());
    }

    @Test
    void toItemForRequestDto() {
        Item item = new Item(1L, "test", "description", true, 1L, 1L);

        ItemForRequestDto actualRequestItem = ItemMapper.toItemForRequestDto(item);

        assertEquals(item.getId(), actualRequestItem.getId());
        assertEquals(item.getName(), actualRequestItem.getName());
        assertEquals(item.getDescription(), actualRequestItem.getDescription());
        assertEquals(item.isAvailable(), actualRequestItem.getAvailable());
        assertEquals(item.getRequestId(), actualRequestItem.getRequestId());
    }
}