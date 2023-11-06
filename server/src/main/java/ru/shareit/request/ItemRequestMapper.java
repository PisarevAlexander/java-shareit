package ru.shareit.request;

/**
 * The type Item request mapper
 */

public class ItemRequestMapper {

    /**
     * Item request to item request dto
     * @param itemRequest the item request
     * @return the item request dto
     */

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    /**
     * Item request dto to item request
     * @param itemRequestDto the item request dto
     * @return the item request
     */

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        return itemRequest;
    }
}