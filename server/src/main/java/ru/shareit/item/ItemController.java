package ru.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shareit.comment.Comment;
import ru.shareit.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Item controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * Create item.
     * POST /items
     * @param itemDto the item dto
     * @param userId  the user id
     * @return the item
     */

    @PostMapping
    public Item create(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.create(itemDto, userId);
    }

    /**
     * Update item.
     * PATCH /items/{itemId}
     * @param itemDto the item dto
     * @param itemId  the item id
     * @param userId  the user id
     * @return the item
     */

    @PatchMapping("/{itemId}")
    public Item update(@RequestBody ItemDto itemDto, @PathVariable long itemId,
                       @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.update(itemDto, itemId, userId);
    }

    /**
     * Find item item dto.
     * GET /items/{itemId}
     * @param itemId the item id
     * @param userId the user id
     * @return the item dto
     */

    @GetMapping("/{itemId}")
    public ItemDto findItem(@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemDtoById(itemId, userId);
    }

    /**
     * Find all list.
     * GET /items
     * @param userId the user id
     * @param from   the from
     * @param size   the size
     * @return the list of items
     */

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam Integer from,
                                 @RequestParam Integer size) {
        return itemService.getAll(userId, from, size);
    }

    /**
     * Find item by text list.
     * GET /items/search
     * @param text the text
     * @param from the from
     * @param size the size
     * @return the list
     */

    @GetMapping("/search")
    public List<Item> findItemByText(@RequestParam String text, @RequestParam Integer from,
                                     @RequestParam Integer size) {
        return itemService.search(text, from, size);
    }

    /**
     * Save comment comment.
     * POST /items/{itemId}/comment
     * @param commentDto the comment dto
     * @param itemId     the item id
     * @param userId     the user id
     * @return the comment
     */

    @PostMapping("/{itemId}/comment")
    public Comment saveComment(@RequestBody CommentDto commentDto, @PathVariable Long itemId,
                               @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.saveCommit(commentDto, itemId, userId, LocalDateTime.now());
    }
}