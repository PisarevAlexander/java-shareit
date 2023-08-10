package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingMapperTest {

    @Autowired
    private static BookingMapper bookingMapper;

    @Test
    void toBooking() {
        BookingDto bookingDto = new BookingDto(1L, LocalDateTime.now(), LocalDateTime.now());

        Booking booking = bookingMapper.toBooking(bookingDto, new Item(), new User());

        assertEquals(bookingDto.getStart(), booking.getStart());
        assertEquals(bookingDto.getEnd(), booking.getEnd());
    }

    @Test
    void bookingForItemDto() {
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now(),
                new Item(), new User(1L, "test", "test@gmail.com"), Status.APPROVED);

        BookingForItemDto bookingForItemDto = bookingMapper.bookingForItemDto(booking);

        assertEquals(booking.getId(), bookingForItemDto.getId());
        assertEquals(booking.getBooker().getId(), bookingForItemDto.getBookerId());
    }
}