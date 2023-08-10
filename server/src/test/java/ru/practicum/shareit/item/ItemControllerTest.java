package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingForItemDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ItemDto itemDto;
    private Item item;

    @BeforeEach
    void setUp() {
        itemDto = new ItemDto(1L, "test", "description", true, 1L,
                new BookingForItemDto(), new BookingForItemDto(), new ArrayList<>());
        item = new Item(1L, "test", "description", true, 1L, 1L);
    }

    @Test
    void create() {
        when(itemService.create(itemDto, 1L))
                .thenReturn(item);

        Item actualItem = itemController.create(itemDto, 1L);

        assertEquals(item, actualItem);
    }

    @Test
    void update() {
        when(itemService.update(itemDto, 1L, 1L))
                .thenReturn(item);

        Item updatedItem = itemController.update(itemDto, 1L, 1L);

        assertEquals(item, updatedItem);
    }

    @Test
    void findItem() {
        when(itemService.getItemDtoById(1L, 1L))
                .thenReturn(itemDto);

        ItemDto actualItem = itemController.findItem(1L, 1L);

        assertEquals(itemDto, actualItem);
    }

    @Test
    void findAll() {
        when(itemService.getAll(1L, 1, 1))
                .thenReturn(List.of(itemDto));

        List<ItemDto> items = itemController.findAll(1L, 1, 1);

        assertEquals(1, items.size());
        assertEquals(itemDto, items.get(0));
    }

    @Test
    void findItemByText() {
        when(itemService.search("test", 1, 1))
                .thenReturn(List.of(item));

        List<Item> items = itemController.findItemByText("test", 1, 1);

        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
    }
}