package ru.shareit.UT.item;

import org.junit.jupiter.api.Test;
import ru.shareit.item.Item;
import ru.shareit.item.ItemDto;
import ru.shareit.item.ItemForRequestDto;
import ru.shareit.item.ItemMapper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Item mapper test
 */

class ItemMapperTest {


    /**
     * To item dto with not null request test
     */

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

    /**
     * To item dto with null request test
     */

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

    /**
     * To item test
     */

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

    /**
     * To item for request dto test
     */

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