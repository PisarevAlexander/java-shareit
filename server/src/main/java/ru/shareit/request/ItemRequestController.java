package ru.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.shareit.OffsetBasedPageRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Item request controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    /**
     * Create item request.
     * POST /requests
     * @param itemRequestDto the item request dto
     * @param userId         the user id
     * @return the item request
     */

    @PostMapping
    public ItemRequest create(@RequestBody ItemRequestDto itemRequestDto,
                              @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.create(itemRequestDto, userId, LocalDateTime.now());
    }

    /**
     * Find all request list.
     * GET /requests
     * @param userId the user id
     * @return the list
     */

    @GetMapping
    public List<ItemRequestDto> findAllRequest(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getAll(userId);
    }

    /**
     * Find request list.
     * GET /requests/all
     * @param from   the from
     * @param size   the size
     * @param userId the user id
     * @return the list
     */

    @GetMapping("/all")
    public List<ItemRequestDto> findRequest(@RequestParam Integer from, @RequestParam Integer size,
                                            @RequestHeader("X-Sharer-User-Id") long userId) {
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by("created").descending());
        return itemRequestService.getAll(pageable, userId);
    }

    /**
     * Find request by id item request dto.
     * GET /requests/{requestId}
     * @param requestId the request id
     * @param userId    the user id
     * @return the item request dto
     */

    @GetMapping("/{requestId}")
    public ItemRequestDto findRequestById(@PathVariable long requestId,
                                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getById(requestId, userId);
    }
}