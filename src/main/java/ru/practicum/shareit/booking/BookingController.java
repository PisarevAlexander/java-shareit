package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BadRequestException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking create(@Valid @RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") long userId) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().equals(bookingDto.getEnd())) {
            throw new BadRequestException("Время начала не может быть позже окончания");
        }
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking update(@PathVariable Long bookingId, @RequestParam Boolean approved,
                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.update(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking findBooking(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping
    public List<Booking> findAllUserBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                        @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllUserBooking(userId, state);
    }

    @GetMapping("owner")
    public List<Booking> findAllOwnerBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllOwnerBooking(userId, state);
    }
}
