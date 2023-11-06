package ru.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Booking dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}
