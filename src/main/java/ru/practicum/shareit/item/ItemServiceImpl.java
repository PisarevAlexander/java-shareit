package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.OffsetBasedPageRequest;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Item create(ItemDto itemDto, long userId) {
        userService.getById(userId);

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        return itemRepository.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItemDtoById(long itemId, long userId) {
        ItemDto itemDto = ItemMapper.toItemDto(itemRepository.findItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмета с id " + itemId + " не существует")));

        List<Booking> bookings = bookingRepository.findAllByItem_IdAndItem_OwnerAndStatus(itemId, userId, Status.APPROVED);

        LocalDateTime localDateTime = LocalDateTime.now();

        Booking lastBooking = bookings.stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .filter(b -> b.getStart().isBefore(localDateTime))
                .findFirst()
                .orElse(null);

        Booking nextBooking = bookings.stream()
                .sorted(Comparator.comparing(Booking::getStart))
                .filter(b -> b.getStart().isAfter(localDateTime))
                .findFirst()
                .orElse(null);

        if (lastBooking != null) {
            itemDto.setLastBooking(BookingMapper.bookingForItemDto(lastBooking));
        }
        if (nextBooking != null) {
            itemDto.setNextBooking(BookingMapper.bookingForItemDto(nextBooking));
        }

        itemDto.setComments(commentRepository.findAllByItem_Id(itemId));

        return itemDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemById(long itemId) {
        return itemRepository.findItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмета с id " + itemId + " не существует"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getAll(long userId, int from, int size) {
        List<ItemDto> itemsDto = new ArrayList<>();
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        List<Item> items = itemRepository.findAllByOwner(userId, pageable);
        for (Item item : items) {
            itemsDto.add(ItemMapper.toItemDto(item));
        }
        List<Booking> bookings = bookingRepository.findAllByItem_OwnerAndStatus(userId, Status.APPROVED);
        List<Comment> comments = commentRepository.findAll();
        LocalDateTime localDateTime = LocalDateTime.now();

        if (!bookings.isEmpty()) {
            for (ItemDto itemDto : itemsDto) {
                Booking lastBooking = bookings.stream()
                        .sorted(Comparator.comparing(Booking::getStart).reversed())
                        .filter(b -> b.getStart().isBefore(localDateTime) && b.getItem().getId().equals(itemDto.getId()))
                        .findFirst()
                        .orElse(null);

                Booking nextBooking = bookings.stream()
                        .sorted(Comparator.comparing(Booking::getStart))
                        .filter(b -> b.getStart().isAfter(localDateTime) && b.getItem().getId().equals(itemDto.getId()))
                        .findFirst()
                        .orElse(null);

                List<Comment> itemCommits = comments.stream()
                        .filter(comment -> comment.getItem().getId().equals(itemDto.getId()))
                        .collect(Collectors.toList());

                itemDto.setComments(itemCommits);

                if (lastBooking != null) {
                    itemDto.setLastBooking(BookingMapper.bookingForItemDto(lastBooking));
                }
                if (nextBooking != null) {
                    itemDto.setNextBooking(BookingMapper.bookingForItemDto(nextBooking));
                }
            }
        }
        return itemsDto;
    }

    @Override
    @Transactional
    public Item update(ItemDto itemDto, long itemId, long userId) {
        Item item = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмета с id " + itemId + " не существует"));
        if (item.getOwner() != userId) {
            throw new NotFoundException("Пользователь с id " + userId + " не может редактировать предмет с id " + itemId);
        }

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return itemRepository.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> search(String text, int from, int size) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        return itemRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(text,
                text, pageable);
    }

    @Override
    @Transactional
    public Comment saveCommit(CommentDto commentDto, long itemId, long userId, LocalDateTime localDateTime) {
        Item item = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмета с id " + itemId + " не существует"));
        List<Booking> bookings = bookingRepository.findBookingByBooker_IdAndItem_IdAndAndEndBefore(userId, itemId,
                localDateTime);

        if (bookings.isEmpty()) {
            throw new BadRequestException("Не верный " + itemId + " или " + userId);
        }

        Comment comment = CommentMapper.toComment(commentDto);
        comment.setItem(item);
        comment.setAuthorName(bookings.get(0).getBooker().getName());
        comment.setCreated(localDateTime);

        return commentRepository.save(comment);
    }
}