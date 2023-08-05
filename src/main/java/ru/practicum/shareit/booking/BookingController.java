package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BadRequestException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Validated
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
    public Booking update(@PathVariable long bookingId, @RequestParam Boolean approved,
                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.update(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking findBooking(@PathVariable long bookingId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping
    public List<Booking> findAllUserBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @RequestParam(defaultValue = "ALL") String state,
                                            @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                            @RequestParam(defaultValue = "20") @Min(1) Integer size) {
        return bookingService.getAllUserBooking(userId, state, from, size);
    }

    @GetMapping("owner")
    public List<Booking> findAllOwnerBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestParam(defaultValue = "ALL") String state,
                                             @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                             @RequestParam(defaultValue = "20") @Min(1) Integer size) {
        return bookingService.getAllOwnerBooking(userId, state, from, size);
    }
}
