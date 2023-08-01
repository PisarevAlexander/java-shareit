package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.ItemForRequestDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    private Long id;
    @NotBlank(message = "description can't be blank")
    private String description;
    private LocalDateTime created;
    private List<ItemForRequestDto> items;


    public void addRequestItem(ItemForRequestDto itemForRequestDto) {
        items.add(itemForRequestDto);
    }
}
