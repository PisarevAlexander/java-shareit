package ru.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Booking for item dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingForItemDto {

    private long id;
    private long bookerId;
}
