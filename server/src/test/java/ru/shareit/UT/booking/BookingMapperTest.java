package ru.shareit.UT.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shareit.booking.*;
import ru.shareit.item.Item;
import ru.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Booking mapper test.
 */

class BookingMapperTest {

    @Autowired
    private static BookingMapper bookingMapper;

    /**
     * To booking
     */

    @Test
    void toBooking() {
        BookingDto bookingDto = new BookingDto(1L, LocalDateTime.now(), LocalDateTime.now());

        Booking booking = bookingMapper.toBooking(bookingDto, new Item(), new User());

        assertEquals(bookingDto.getStart(), booking.getStart());
        assertEquals(bookingDto.getEnd(), booking.getEnd());
    }

    /**
     * Booking for item dto
     */

    @Test
    void bookingForItemDto() {
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now(),
                new Item(), new User(1L, "test", "test@gmail.com"), Status.APPROVED);

        BookingForItemDto bookingForItemDto = bookingMapper.bookingForItemDto(booking);

        assertEquals(booking.getId(), bookingForItemDto.getId());
        assertEquals(booking.getBooker().getId(), bookingForItemDto.getBookerId());
    }
}