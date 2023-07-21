package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingForItemDto;
import ru.practicum.shareit.comment.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    @NotBlank(message = "name can't be blank")
    private String name;
    @NotBlank(message = "description can't be blank")
    private String description;
    @NotNull
    private Boolean available;
    private String request;

    private BookingForItemDto lastBooking;
    private BookingForItemDto nextBooking;
    private List<Comment> comments;
}
