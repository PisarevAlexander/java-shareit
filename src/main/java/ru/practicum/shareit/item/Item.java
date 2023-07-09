package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private boolean available;
    private long owner;
    private String request;
}
