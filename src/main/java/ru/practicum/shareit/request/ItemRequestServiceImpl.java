package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.OffsetBasedPageRequest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemForRequestDto;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    public final UserService userService;

    @Override
    @Transactional
    public ItemRequest create(ItemRequestDto itemRequestDto, long userId, LocalDateTime time) {
        userService.getById(userId);

        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setUser(userId);
        itemRequest.setCreated(time);
        return itemRequestRepository.save(itemRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRequestDto> getAll(long userId) {
        userService.getById(userId);
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByUserOrderByCreatedDesc(userId);

        return getItemRequests(itemRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRequestDto> getAll(Pageable pageable, long userId) {
        userService.getById(userId);

        Page<ItemRequest> requests = itemRequestRepository.findAllByUserNot(userId, pageable);
        List<ItemRequest> itemRequests = requests.getContent();

        return getItemRequests(itemRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemRequestDto getById(long requestId, long userId) {
        userService.getById(userId);

        ItemRequestDto itemRequestsDto = ItemRequestMapper.toItemRequestDto(itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с Id " + requestId + " не найден")));
        List<Item> items = itemRepository.findAllByRequestId(requestId);

        if (!items.isEmpty()) {
            List<ItemForRequestDto> itemForRequestDtos = items.stream()
                    .map(ItemMapper::toItemForRequestDto)
                    .collect(Collectors.toList());
            itemRequestsDto.setItems(itemForRequestDtos);
        }
        return itemRequestsDto;
    }

    private List<ItemRequestDto> getItemRequests(List<ItemRequest> itemRequests) {
        if (itemRequests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> requestIds = itemRequests.stream()
                .map(ItemRequest::getId)
                .collect(Collectors.toList());

        List<Item> items = itemRepository.findAllByRequestIdIn(requestIds);

        List<ItemRequestDto> itemRequestDtos = itemRequests.stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());

        for (ItemRequestDto itemRequestDto : itemRequestDtos) {
            itemRequestDto.setItems(new ArrayList<>());

            if (!items.isEmpty()) {
                for (Item item : items) {
                    if (item.getRequestId().equals(itemRequestDto.getId())) {
                        itemRequestDto.addRequestItem(ItemMapper.toItemForRequestDto(item));
                    }
                }
            }
        }
        return itemRequestDtos;
    }
}