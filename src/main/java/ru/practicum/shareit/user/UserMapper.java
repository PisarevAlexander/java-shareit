package ru.practicum.shareit.user;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getName(), user.getEmail());
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;

    }
}
