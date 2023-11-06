package ru.shareit.request;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Item request service
 */

public interface ItemRequestService {

    /**
     * Create item request
     * @param itemRequestDto the item request dto
     * @param userId         the user id
     * @param time           the time
     * @return the item request
     */

    ItemRequest create(ItemRequestDto itemRequestDto, long userId, LocalDateTime time);

    /**
     * Get all
     * @param userId the user id
     * @return the items
     */

    List<ItemRequestDto> getAll(long userId);

    /**
     * Get all
     * @param pageable the pageable
     * @param userId   the user id
     * @return the items
     */

    List<ItemRequestDto> getAll(Pageable pageable, long userId);

    /**
     * Get by id
     * @param requestId the request id
     * @param userId    the user id
     * @return the item
     */

    ItemRequestDto getById(long requestId, long userId);
}