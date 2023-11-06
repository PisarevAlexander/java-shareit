package ru.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shareit.comment.CommentDto;
import ru.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * The type Item controller
 */

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    /**
     * Create response entity
     * POST /items
     * @param itemDto the item dto
     * @param userId  the user id
     * @return the response entity
     */

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ItemDto itemDto,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Creating item {}", itemDto);
        return itemClient.create(itemDto, userId);
    }

    /**
     * Update response entity
     * PATCH /items/{itemId}
     * @param itemDto the item dto
     * @param itemId  the item id
     * @param userId  the user id
     * @return the response entity
     */

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestBody ItemDto itemDto, @PathVariable long itemId,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Update item {}, itemId={}, userId={}", itemDto, itemId, userId);
        return itemClient.update(itemDto, itemId, userId);
    }

    /**
     * Find item response entity.
     * GET /items/{itemId}
     * @param itemId the item id
     * @param userId the user id
     * @return the response entity
     */

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItem(@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get item {}", itemId);
        return itemClient.findItem(itemId, userId);
    }

    /**
     * Find all item response entity
     * GET /items
     * @param userId the user id
     * @param from   the from
     * @param size   the size
     * @return the response entity
     */

    @GetMapping
    public ResponseEntity<Object> findAllItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get items with userId={}, from={}, size={}", userId, from, size);
        return itemClient.findAllItem(userId, from, size);
    }

    /**
     * Find item by text response entity
     * GET /items/search
     * @param text the text
     * @param from the from
     * @param size the size
     * @return the response entity
     */

    @GetMapping("/search")
    public ResponseEntity<Object> findItemByText(@RequestParam String text,
                                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get items with test={}, from={}, size={}", text, from, size);
        return itemClient.findItemByText(text, from, size);
    }

    /**
     * Save comment response entity.
     * POST /items/{itemId}/comment
     * @param commentDto the comment dto
     * @param itemId     the item id
     * @param userId     the user id
     * @return the response entity
     */

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> saveComment(@RequestBody @Valid CommentDto commentDto, @PathVariable long itemId,
                                              @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Creating comment {} for itemId={}", commentDto, itemId);
        return itemClient.saveComment(commentDto, itemId, userId);
    }
}