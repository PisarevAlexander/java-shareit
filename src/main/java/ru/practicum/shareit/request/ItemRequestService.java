package ru.practicum.shareit.request;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRequestService {

    ItemRequest create(ItemRequestDto itemRequestDto, long userId, LocalDateTime time);

    List<ItemRequestDto> getAll(long userId);

    List<ItemRequestDto> getAllWithSize(int from, int size, long userId);

    ItemRequestDto getById(long requestId, long userId);
}
