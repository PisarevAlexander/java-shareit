package ru.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shareit.booking.BookingForItemDto;
import ru.shareit.comment.Comment;

import java.util.List;

/**
 * The type Item dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;

    private BookingForItemDto lastBooking;
    private BookingForItemDto nextBooking;
    private List<Comment> comments;
}
