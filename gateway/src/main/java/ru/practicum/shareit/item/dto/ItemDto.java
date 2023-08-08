package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    @NotBlank(message = "name can't be blank")
    private String name;
    @NotBlank(message = "description can't be blank")
    private String description;
    @NotNull
    private Boolean available;
    private Long requestId;
}