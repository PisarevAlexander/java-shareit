package ru.shareit.booking.dto;

import java.util.Optional;

/**
 * The enum Booking state
 */

public enum BookingState {

    ALL,
    CURRENT,
    FUTURE,
    PAST,
    REJECTED,
    WAITING;

    /**
     * From optional
     * @param stringState the string state
     * @return the optional
     */

    public static Optional<BookingState> from(String stringState) {
        for (BookingState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}