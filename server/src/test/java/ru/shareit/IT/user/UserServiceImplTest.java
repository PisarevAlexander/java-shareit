package ru.shareit.IT.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shareit.exception.NotFoundException;
import ru.shareit.user.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * User service impl test
 */

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;
    private User user;
    private UserDto userDto;


    /**
     * Sets up
     */

    @BeforeEach
    public void setUp() {
        user = new User(1, "test", "test@gmail.com");
        userDto = new UserDto("test", "test@gmail.com");
    }

    /**
     * Create test
     */

    @Test
    void create() {
        when(repository.save(UserMapper.toUser(userDto)))
                .thenReturn(user);

        User actualUser = userService.create(userDto);

        assertEquals(user, actualUser);
    }

    /**
     * Gets by id when user found then return user test
     */

    @Test
    void getById_whenUserFound_thenReturnUser() {
        when(repository.findUserById(1L))
                .thenReturn(Optional.of(user));

        User actualUser = userService.getById(1L);

        assertEquals(user, actualUser);
    }

    /**
     * Gets by id when user not found then return exception test
     */

    @Test
    void getById_whenUserNotFound_thenReturnException() {
        when(repository.findUserById(2L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(2L));
    }

    /**
     * Get all test
     */

    @Test
    void getAll() {
        when(repository.findAll())
                .thenReturn(List.of(user));

        List<User> users = userService.getAll();

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    /**
     * Update when user not found then return exception test
     */

    @Test
    void update_whenUserNotFound_thenReturnException() {
        when(repository.findUserById(2L))
                .thenReturn(Optional.empty());

        verify((repository), never()).save(user);
        assertThrows(NotFoundException.class, () -> userService.getById(2L));
    }

    /**
     * Update when user change then return changed user test
     */

    @Test
    void update_whenUserChange_thenReturnChangedUser() {
        UserDto changedUser = new UserDto("test1", "test1@gmail.com");
        when(repository.findUserById(1L))
                .thenReturn(Optional.of(user));
        when((repository.save(user)))
                .thenReturn(user);

        User updatedUser = userService.update(changedUser, 1L);

        assertEquals(updatedUser.getName(), changedUser.getName());
        assertEquals(updatedUser.getEmail(), changedUser.getEmail());
    }
}