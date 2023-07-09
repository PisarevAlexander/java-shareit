package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ItemDto {

    @NotBlank(message = "name can't be blank")
    private String name;
    @NotBlank(message = "description can't be blank")
    private String description;
    @NotNull
    private Boolean available;
    private String request;
}
