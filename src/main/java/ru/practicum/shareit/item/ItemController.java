package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public Item create(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestBody ItemDto itemDto, @PathVariable Long itemId,
                       @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public Item findItem(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getById(itemId);
    }

    @GetMapping
    public List<Item> findAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getAll(userId);
    }

    @GetMapping("/search")
    public List<Item> findItemByText(@RequestParam String text) {
        return itemService.search(text);
    }
}