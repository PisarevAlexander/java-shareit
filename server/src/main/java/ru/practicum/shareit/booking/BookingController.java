package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking create(@RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking update(@PathVariable long bookingId, @RequestParam Boolean approved,
                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.update(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking findBooking(@PathVariable long bookingId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping
    public List<Booking> findAllUserBooking(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String state,
                                            @RequestParam Integer from, @RequestParam Integer size) {
        return bookingService.getAllUserBooking(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<Booking> findAllOwnerBooking(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String state,
                                             @RequestParam Integer from, @RequestParam Integer size) {
        return bookingService.getAllOwnerBooking(userId, state, from, size);
    }
}
