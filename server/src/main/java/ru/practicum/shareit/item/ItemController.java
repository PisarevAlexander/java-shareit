package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item create(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestBody ItemDto itemDto, @PathVariable long itemId,
                       @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findItem(@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemDtoById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam Integer from,
                                 @RequestParam Integer size) {
        return itemService.getAll(userId, from, size);
    }

    @GetMapping("/search")
    public List<Item> findItemByText(@RequestParam String text, @RequestParam Integer from,
                                     @RequestParam Integer size) {
        return itemService.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public Comment saveComment(@RequestBody CommentDto commentDto, @PathVariable Long itemId,
                               @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.saveCommit(commentDto, itemId, userId, LocalDateTime.now());
    }
}