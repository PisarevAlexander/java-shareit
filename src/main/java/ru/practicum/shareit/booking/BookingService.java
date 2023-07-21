package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {

    Booking create(BookingDto bookingDto, long userId);

    Booking update(long bookingId, boolean approved, long userId);

    Booking getById(long bookingId, long userId);

    List<Booking> getAllUserBooking(long userId, String state);

    List<Booking> getAllOwnerBooking(long userId, String state);
}
