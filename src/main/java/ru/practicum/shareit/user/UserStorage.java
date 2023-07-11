package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserStorage {

    private long id = 1L;
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();

    public Optional<User> findById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public List<User> fndAll() {
        return new ArrayList<User>(users.values());
    }

    public Optional<User> create(UserDto userDto) {
        if (!emails.contains(userDto.getEmail())) {
            User user = new User(id, userDto.getName(), userDto.getEmail());
            id++;
            users.put(user.getId(), user);
            emails.add(user.getEmail());
            return Optional.ofNullable(users.get(user.getId()));
        }
        return Optional.ofNullable(null);
    }

    public Optional<User> update(long userId, UserDto userDto) {
            User updatedUser = users.get(userId);
        if (!emails.contains(userDto.getEmail()) || updatedUser.getEmail().equals(userDto.getEmail())) {
            if (userDto.getName() != null) {
                updatedUser.setName(userDto.getName());
            }

            if (userDto.getEmail() != null) {
                emails.remove(updatedUser.getEmail());
                updatedUser.setEmail(userDto.getEmail());
                emails.add(updatedUser.getEmail());
            }

            users.put(updatedUser.getId(), updatedUser);
            return Optional.ofNullable(updatedUser);
        }
        return Optional.ofNullable(null);
    }

    public void delete(long userId) {
        emails.remove(users.get(userId).getEmail());
        users.remove(userId);
    }
}
