package ru.shareit.user;

import java.util.List;

/**
 * The interface User service
 */

public interface UserService {

    /**
     * Create user
     * @param userDto the user dto
     * @return the user
     */

    User create(UserDto userDto);

    /**
     * Get by id
     * @param userId the user id
     * @return the user
     */

    User getById(long userId);

    /**
     * Get all
     * @return the all users
     */

    List<User> getAll();

    /**
     * Update user
     * @param userDto the user dto
     * @param userId  the user id
     * @return the user
     */

    User update(UserDto userDto, long userId);

    /**
     * Delete
     * @param userId the user id
     */

    void delete(long userId);
}