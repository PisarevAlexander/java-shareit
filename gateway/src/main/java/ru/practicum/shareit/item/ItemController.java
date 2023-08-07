package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ItemDto itemDto,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Creating item {}", itemDto);
        return itemClient.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestBody ItemDto itemDto, @PathVariable long itemId,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Update item {}, itemId={}, userId={}", itemDto, itemId, userId);
        return itemClient.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItem(@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get item {}", itemId);
        return itemClient.findItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get items with userId={}, from={}, size={}", userId, from, size);
        return itemClient.findAllItem(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findItemByText(@RequestParam String text,
                                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get items with test={}, from={}, size={}", text, from, size);
        return itemClient.findItemByText(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> saveComment(@RequestBody @Valid CommentDto commentDto, @PathVariable long itemId,
                                              @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Creating comment {} for itemId={}", commentDto, itemId);
        return itemClient.saveComment(commentDto, itemId, userId);
    }
}