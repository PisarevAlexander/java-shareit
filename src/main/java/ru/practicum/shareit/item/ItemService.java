package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;

import java.util.List;

public interface ItemService {

    Item create(ItemDto itemDto, long userId);

    ItemDto getById(long itemId, long userId);

    List<ItemDto> getAll(long userId);

    Item update(ItemDto itemDto, long itemId, long userId);

    List<Item> search(String text);

    Comment saveCommit(CommentDto commentDto, long itemId, long userId);
}
