package ru.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The type Book item request dto
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {

    @NotNull(message = "itemId can't be null")
    private long itemId;

    @NotNull(message = "start can't be null")
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull(message = "end can't be null")
    @Future
    private LocalDateTime end;
}