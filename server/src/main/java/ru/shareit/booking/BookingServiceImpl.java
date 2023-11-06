package ru.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shareit.OffsetBasedPageRequest;
import ru.shareit.exception.BadRequestException;
import ru.shareit.exception.NotFoundException;
import ru.shareit.item.Item;
import ru.shareit.item.ItemService;
import ru.shareit.user.User;
import ru.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Booking service
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    public final BookingRepository bookingRepository;
    public final UserService userService;
    public final ItemService itemService;

    @Override
    @Transactional
    public Booking create(BookingDto bookingDto, long userId) {
        User user = userService.getById(userId);
        Item item = itemService.getItemById(bookingDto.getItemId());

        if (user.getId() == item.getOwner()) {
            throw new NotFoundException("Пользователь не может забронировать свой предмет");
        }

        if (item.isAvailable()) {
            Booking booking = BookingMapper.toBooking(bookingDto, item, user);
            booking.setStatus(Status.WAITING);
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Item с " + item.getId() + " не доступен");
        }
    }

    @Override
    @Transactional
    public Booking update(long bookingId, boolean approved, long userId) {
        userService.getById(userId);
        Booking booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с Id " + bookingId + " не найден"));

        if (booking.getItem().getOwner() != userId) {
            throw new NotFoundException("Пользователь с Id" + userId + " не имеет доступа");
        } else if (booking.getStatus().equals(Status.APPROVED) && approved) {
            throw new BadRequestException("Бронь с Id " + bookingId + " уже подтверждена");
        }

        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getById(long bookingId, long userId) {
        Booking booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с Id " + bookingId + " не найден"));

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner() != userId) {
            throw new NotFoundException("Пользователь с Id" + userId + " не имеет доступа");
        }
        return booking;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAllUserBooking(long userId, String state, int from, int size) {
        userService.getById(userId);

        switch (state) {
            case "ALL":
                Pageable pageable = new OffsetBasedPageRequest(from, size);
                return bookingRepository.findAllByBooker_IdOrderByStartDesc(userId, pageable);
            case "CURRENT":
                return bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                        LocalDateTime.now(), LocalDateTime.now());
            case "PAST":
                return bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.findAllByBooker_IdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.findAllByBooker_IdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByBooker_IdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            default:
                throw new BadRequestException("Unknown state: " + state);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAllOwnerBooking(long userId, String state, int from, int size) {
        userService.getById(userId);

        switch (state) {
            case "ALL":
                Pageable pageable = new OffsetBasedPageRequest(from, size);
                return bookingRepository.findAllByItem_OwnerOrderByStartDesc(userId, pageable);
            case "CURRENT":
                return bookingRepository.findAllByItem_OwnerAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                        LocalDateTime.now(), LocalDateTime.now());
            case "PAST":
                return bookingRepository.findAllByItem_OwnerAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.findAllByItem_OwnerAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.findAllByItem_OwnerAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByItem_OwnerAndStatusOrderByStartDesc(userId, Status.REJECTED);
            default:
                throw new BadRequestException("Unknown state: " + state);
        }
    }
}