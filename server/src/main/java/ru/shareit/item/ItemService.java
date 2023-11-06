package ru.shareit.item;

import ru.shareit.comment.Comment;
import ru.shareit.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Item service
 */

public interface ItemService {

    /**
     * Create item.
     * @param itemDto the item dto
     * @param userId  the user id
     * @return the item
     */

    Item create(ItemDto itemDto, long userId);

    /**
     * Get item dto by id
     * @param itemId the item id
     * @param userId the user id
     * @return the item dto by id
     */

    ItemDto getItemDtoById(long itemId, long userId);

    /**
     * Get item by id
     * @param itemId the item id
     * @return the item by id
     */

    Item getItemById(long itemId);

    /**
     * Get all
     * @param userId the user id
     * @param from   the from
     * @param size   the size
     * @return the list of items
     */

    List<ItemDto> getAll(long userId, int from, int size);

    /**
     * Update item
     * @param itemDto the item dto
     * @param itemId  the item id
     * @param userId  the user id
     * @return the item
     */

    Item update(ItemDto itemDto, long itemId, long userId);

    /**
     * Search list
     * @param text the text
     * @param from the from
     * @param size the size
     * @return the list of items
     */

    List<Item> search(String text, int from, int size);

    /**
     * Save commit comment
     * @param commentDto    the comment dto
     * @param itemId        the item id
     * @param userId        the user id
     * @param localDateTime the local date time
     * @return the comment
     */

    Comment saveCommit(CommentDto commentDto, long itemId, long userId, LocalDateTime localDateTime);
}