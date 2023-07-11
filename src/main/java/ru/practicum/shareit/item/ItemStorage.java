package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ItemStorage {

    private long id = 1L;
    private final Map<Long, Item> items = new HashMap<>();

    public Item create(ItemDto itemDto, long userId) {
        Item item = new Item(id, itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(), userId,
                itemDto.getRequest());
        id++;
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    public Optional<Item> findById(long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    public List<Item> getAll(long userId) {
        List<Item> userItems = new ArrayList<>();
        for (Item item: items.values()) {
            if (item.getOwner() == userId) {
                userItems.add(item);
            }
        }
        return userItems;
    }

    public Item update(ItemDto itemDto, long itemId) {
        Item updatedItem = items.get(itemId);
        if (itemDto.getName() != null) {
            updatedItem.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            updatedItem.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            updatedItem.setAvailable(itemDto.getAvailable());
        }

        items.put(updatedItem.getId(), updatedItem);
        return updatedItem;
    }

    public List<Item> search(String text) {
        List<Item> searchItems = new ArrayList<>();
        for (Item item: items.values()) {
            if (item.isAvailable() && !text.isBlank() && (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase()))) {
                searchItems.add(item);
            }
        }
        return searchItems;
    }
}