package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;
    private User user;
    private UserDto userDto;


    @BeforeEach
    public void setUp() {
        user = new User(1, "test", "test@gmail.com");
        userDto = new UserDto("test", "test@gmail.com");
    }

    @Test
    void create() {
        when(repository.save(UserMapper.toUser(userDto)))
                .thenReturn(user);

        User actualUser = userService.create(userDto);

        assertEquals(user, actualUser);
    }

    @Test
    void getById_whenUserFound_thenReturnUser() {
        when(repository.findUserById(1l))
                .thenReturn(Optional.of(user));

        User actualUser = userService.getById(1l);

        assertEquals(user, actualUser);
    }

    @Test
    void getById_whenUserNotFound_thenReturnException() {
        when(repository.findUserById(2l))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(2L));
    }

    @Test
    void getAll() {
        when(repository.findAll())
                .thenReturn(List.of(user));

        List<User> users = userService.getAll();

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void update_whenUserNotFound_thenReturnException() {
        when(repository.findUserById(2l))
                .thenReturn(Optional.empty());

        verify((repository), never()).save(user);
        assertThrows(NotFoundException.class, () -> userService.getById(2L));
    }

    @Test
    void update_whenUserChange_thenReturnChangedUser() {
        UserDto changedUser = new UserDto("test1", "test1@gmail.com");
        when(repository.findUserById(1l))
                .thenReturn(Optional.of(user));
        when((repository.save(user)))
                .thenReturn(user);

        User updatedUser = userService.update(changedUser, 1L);

        assertEquals(updatedUser.getName(), changedUser.getName());
        assertEquals(updatedUser.getEmail(), changedUser.getEmail());
    }
}