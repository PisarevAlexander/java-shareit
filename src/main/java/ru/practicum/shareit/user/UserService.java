package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    User create(UserDto userDto);

    User getById(long userId);

    List<User> getAll();

    User update(UserDto userDto, long userId);

    void delete(long userId);
}
