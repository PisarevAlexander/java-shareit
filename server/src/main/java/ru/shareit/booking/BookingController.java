package ru.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Booking controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create booking.
     * POST /bookings
     * @param bookingDto the booking dto
     * @param userId     the user id
     * @return the booking
     */

    @PostMapping
    public Booking create(@RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.create(bookingDto, userId);
    }

    /**
     * Update booking.
     * PATCH /bookings/{bookingId}
     * @param bookingId the booking id
     * @param approved  the approved
     * @param userId    the user id
     * @return the booking
     */

    @PatchMapping("/{bookingId}")
    public Booking update(@PathVariable long bookingId, @RequestParam Boolean approved,
                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.update(bookingId, approved, userId);
    }

    /**
     * Find booking booking.
     * GET /bookings/{bookingId}
     * @param bookingId the booking id
     * @param userId    the user id
     * @return the booking
     */

    @GetMapping("/{bookingId}")
    public Booking findBooking(@PathVariable long bookingId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getById(bookingId, userId);
    }

    /**
     * Find all user booking list.
     * GET /bookings
     * @param userId the user id
     * @param state  the state
     * @param from   the from
     * @param size   the size
     * @return the list of bookings
     */

    @GetMapping
    public List<Booking> findAllUserBooking(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String state,
                                            @RequestParam Integer from, @RequestParam Integer size) {
        return bookingService.getAllUserBooking(userId, state, from, size);
    }

    /**
     * Find all owner booking list.
     * GET /bookings/owner
     * @param userId the user id
     * @param state  the state
     * @param from   the from
     * @param size   the size
     * @return the list of bookings
     */

    @GetMapping("/owner")
    public List<Booking> findAllOwnerBooking(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String state,
                                             @RequestParam Integer from, @RequestParam Integer size) {
        return bookingService.getAllOwnerBooking(userId, state, from, size);
    }
}