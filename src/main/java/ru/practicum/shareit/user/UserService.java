package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User create(UserDto userDto) {
        return userStorage.create(userDto)
                .orElseThrow(() -> new ConflictException("Пользователь " + userDto + " уже существует"));
    }

    public User getById(long userId) {
        return userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Id " + userId + " не найден"));
    }

    public List<User> getAll() {
        return userStorage.fndAll();
    }

    public User update(UserDto userDto, long userId) {
        User updatedUser = userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Id " + userId + " не найден"));

        return userStorage.update(userId, userDto)
                .orElseThrow(() -> new ConflictException("Пользователь " + updatedUser + " уже существует"));
    }

    public void delete(long userId) {
        userStorage.delete(userId);
    }
}