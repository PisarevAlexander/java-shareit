package ru.practicum.shareit.item;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());
        itemDto.setRequestId(item.getRequestId() != null ? item.getRequestId() : null);
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setRequestId(itemDto.getRequestId());
        return item;
    }

    public static ItemForRequestDto toItemForRequestDto(Item item) {
        ItemForRequestDto itemForRequestDto = new ItemForRequestDto();
                itemForRequestDto.setId(item.getId());
                itemForRequestDto.setName(item.getName());
                itemForRequestDto.setDescription(item.getDescription());
                itemForRequestDto.setAvailable(item.isAvailable());
                itemForRequestDto.setRequestId(item.getRequestId());
        return itemForRequestDto;
    }
}
