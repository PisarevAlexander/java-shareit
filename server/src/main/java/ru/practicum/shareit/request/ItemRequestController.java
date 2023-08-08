package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.OffsetBasedPageRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequest create(@RequestBody ItemRequestDto itemRequestDto,
                              @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.create(itemRequestDto, userId, LocalDateTime.now());
    }

    @GetMapping
    public List<ItemRequestDto> findAllRequest(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getAll(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> findRequest(@RequestParam Integer from, @RequestParam Integer size,
                                            @RequestHeader("X-Sharer-User-Id") long userId) {
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by("created").descending());
        return itemRequestService.getAll(pageable, userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto findRequestById(@PathVariable long requestId,
                                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getById(requestId, userId);
    }
}