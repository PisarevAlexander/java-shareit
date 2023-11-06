package ru.shareit.booking;

import ru.shareit.item.Item;
import ru.shareit.user.User;

/**
 * The type Booking mapper
 */

public class BookingMapper {

    /**
     * Booking DTO to booking
     * @param bookingDto the booking dto
     * @param item       the item
     * @param booker     the booker
     * @return the booking
     */

    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(item);
        booking.setBooker(booker);
        return booking;
    }

    /**
     * Booking to booking for item dto
     * @param booking the booking
     * @return the booking for item dto
     */

    public static BookingForItemDto bookingForItemDto(Booking booking) {
        BookingForItemDto bookingForItemDto = new BookingForItemDto();
        bookingForItemDto.setId(booking.getId());
        bookingForItemDto.setBookerId(booking.getBooker().getId());
        return bookingForItemDto;
    }
}