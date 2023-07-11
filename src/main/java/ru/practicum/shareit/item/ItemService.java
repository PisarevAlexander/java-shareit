package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {

    Item create(ItemDto itemDto, long userId);

    Item getById(long itemId);

    List<Item> getAll(long userId);

    Item update(ItemDto itemDto, long itemId, long userId);

    List<Item> search(String text);
}
