package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping("/{userId}")
    public User findUser(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @GetMapping
    public List<User> findAllUser() {
        return userService.getAll();
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.update(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}