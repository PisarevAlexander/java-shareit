package ru.shareit.booking;

import java.util.List;

/**
 * The interface Booking service
 */

public interface BookingService {

    /**
     * Create booking
     * @param bookingDto the booking dto
     * @param userId     the user id
     * @return the booking
     */

    Booking create(BookingDto bookingDto, long userId);

    /**
     * Update booking
     * @param bookingId the booking id
     * @param approved  the approved
     * @param userId    the user id
     * @return the booking
     */

    Booking update(long bookingId, boolean approved, long userId);

    /**
     * Get by id
     * @param bookingId the booking id
     * @param userId    the user id
     * @return the booking
     */

    Booking getById(long bookingId, long userId);

    /**
     * Get all user booking
     * @param userId the user id
     * @param state  the state
     * @param from   the from
     * @param size   the size
     * @return the all user booking
     */

    List<Booking> getAllUserBooking(long userId, String state, int from, int size);

    /**
     * Get all owner booking
     * @param userId the user id
     * @param state  the state
     * @param from   the from
     * @param size   the size
     * @return the all owner booking
     */

    List<Booking> getAllOwnerBooking(long userId, String state, int from, int size);
}