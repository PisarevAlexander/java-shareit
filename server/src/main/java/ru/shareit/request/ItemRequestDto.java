package ru.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shareit.item.ItemForRequestDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Item request dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemForRequestDto> items;

    /**
     * Add request item.
     * @param itemForRequestDto the item for request dto
     */

    public void addRequestItem(ItemForRequestDto itemForRequestDto) {
        items.add(itemForRequestDto);
    }
}