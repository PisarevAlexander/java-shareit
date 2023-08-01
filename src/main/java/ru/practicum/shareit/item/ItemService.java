package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemService {

    Item create(ItemDto itemDto, long userId);

    ItemDto getItemDtoById(long itemId, long userId);

    Item getItemById(long itemId);

    List<ItemDto> getAll(long userId, int from, int size);

    Item update(ItemDto itemDto, long itemId, long userId);

    List<Item> search(String text, int from, int size);

    Comment saveCommit(CommentDto commentDto, long itemId, long userId, LocalDateTime localDateTime);
}
