package ru.shareit.user;

/**
 * The type User mapper
 */

public class UserMapper {

    /**
     * User dto to user
     * @param userDto the user dto
     * @return the user
     */

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }
}