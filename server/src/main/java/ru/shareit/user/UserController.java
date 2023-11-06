package ru.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    /**
     * Create user.
     * POST /users
     * @param userDto the user dto
     * @return the user
     */

    @PostMapping
    public User create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    /**
     * Find user
     * GET /users/{userId}
     * @param userId the user id
     * @return the user
     */

    @GetMapping("/{userId}")
    public User findUser(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    /**
     * Find all users
     * GET /users
     * @return the list
     */

    @GetMapping
    public List<User> findAllUser() {
        return userService.getAll();
    }

    /**
     * Update user
     * PATCH /users/{userId}
     * @param userId  the user id
     * @param userDto the user dto
     * @return the user
     */

    @PatchMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.update(userDto, userId);
    }

    /**
     * Delete
     * DELETE /users/{userId}
     * @param userId the user id
     */

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}