package ru.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shareit.booking.dto.BookItemRequestDto;
import ru.shareit.booking.dto.BookingState;
import ru.shareit.exception.BadRequestException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * The type Booking controller
 */

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    /**
     * Book item response entity.
     * POST /bookings
     * @param userId     the user id
     * @param requestDto the request dto
     * @return the response entity
     */

    @PostMapping
    public ResponseEntity<Object> bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestBody @Valid BookItemRequestDto requestDto) {
        if (requestDto.getStart().isAfter(requestDto.getEnd()) || requestDto.getStart().equals(requestDto.getEnd())) {
            throw new BadRequestException("Время начала не может быть позже окончания");
        }
        log.info("Creating booking {}, userId={}", requestDto, userId);
        return bookingClient.bookItem(userId, requestDto);
    }

    /**
     * Update response entity.
     * PATCH /bookings/{bookingId}
     * @param bookingId the booking id
     * @param approved  the approved
     * @param userId    the user id
     * @return the response entity
     */

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@PathVariable long bookingId, @RequestParam Boolean approved,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Update booking {}, userId={}", bookingId, userId);
        return bookingClient.update(bookingId, userId, approved);
    }

    /**
     * Get booking
     * GET /bookings/{bookingId}
     * @param userId    the user id
     * @param bookingId the booking id
     * @return the booking
     */

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long bookingId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    /**
     * Find all user booking response entity.
     * GET /bookings
     * @param userId     the user id
     * @param stateParam the state param
     * @param from       the from
     * @param size       the size
     * @return the response entity
     */

    @GetMapping
    public ResponseEntity<Object> findAllUserBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
        return bookingClient.getBookings(userId, state, from, size);
    }

    /**
     * Find all owner booking response entity.
     * GET /bookings/owner
     * @param userId     the user id
     * @param stateParam the state param
     * @param from       the from
     * @param size       the size
     * @return the response entity
     */

    @GetMapping("/owner")
    public ResponseEntity<Object> findAllOwnerBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                      @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
        return bookingClient.getAllOwnerBooking(userId, state, from, size);
    }
}