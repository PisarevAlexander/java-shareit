package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRequestService {

    ItemRequest create(ItemRequestDto itemRequestDto, long userId, LocalDateTime time);

    List<ItemRequestDto> getAll(long userId);

    List<ItemRequestDto> getAll(Pageable pageable, long userId);

    ItemRequestDto getById(long requestId, long userId);
}
