package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemForRequestDto {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;

}