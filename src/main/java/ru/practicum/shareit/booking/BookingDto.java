package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {

    @NotNull(message = "itemId can't be null")
    private Long itemId;
    @NotNull(message = "start can't be null")
    @FutureOrPresent(message = "start can't be in past")
    private LocalDateTime start;
    @NotNull(message = "end can't be null")
    @FutureOrPresent(message = "end can't be in past")
    private LocalDateTime end;
}
