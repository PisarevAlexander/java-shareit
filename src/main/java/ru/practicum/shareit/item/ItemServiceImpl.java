package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public Item create(ItemDto itemDto, long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id" + userId + " не найден"));
        return itemStorage.create(itemDto, userId);
    }

    @Override
    public Item getById(long itemId) {
        return itemStorage.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмета с id " + itemId + " не существует"));
    }

    @Override
    public List<Item> getAll(long userId) {
        return itemStorage.getAll(userId);
    }

    @Override
    public Item update(ItemDto itemDto, long itemId, long userId) {
        Item item = itemStorage.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмета с id " + itemId + " не существует"));
        if (item.getOwner() != userId) {
            throw new NotFoundException("Пользователь с id " + userId + " не может редактировать предмет с id " + itemId);
        }
        return itemStorage.update(itemDto, itemId);
    }

    @Override
    public List<Item> search(String text) {
        return itemStorage.search(text);
    }
}