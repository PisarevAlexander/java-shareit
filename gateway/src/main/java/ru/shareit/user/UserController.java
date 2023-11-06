package ru.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shareit.user.dto.UserDto;

import javax.validation.Valid;

/**
 * The type User controller
 */

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    /**
     * Create response entity.
     * POST /users
     * @param userDto the user dto
     * @return the response entity
     */

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto userDto) {
        log.info("Creating user {}", userDto);
        return userClient.create(userDto);
    }

    /**
     * Find user response entity.
     * GET /users/{userId}
     * @param userId the user id
     * @return the response entity
     */

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findUser(@PathVariable long userId) {
        log.info("Get user {}", userId);
        return userClient.findUser(userId);
    }

    /**
     * Find all user response entity.
     * GET /users
     * @return the response entity
     */

    @GetMapping
    public ResponseEntity<Object> findAllUser() {
        log.info("Get all users");
        return userClient.findAllUser();
    }

    /**
     * Update response entity.
     * PATCH /users/{userId}
     * @param userId  the user id
     * @param userDto the user dto
     * @return the response entity
     */

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable long userId, @RequestBody UserDto userDto) {
        log.info("Update user {}, userId={}", userDto, userId);
        return userClient.update(userId, userDto);
    }

    /**
     * Delete response entity.
     * DELETE /users/{userId}
     * @param userId the user id
     * @return the response entity
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable long userId) {
        return userClient.delete(userId);
    }
}