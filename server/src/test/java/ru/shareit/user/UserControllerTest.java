package ru.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("test", "test@gmail.com");
        user = new User(1, "test", "test@gmail.com");
    }

    @Test
    void createNewUser() {
        when(userService.create(userDto))
                .thenReturn(user);

        User actualUser = userController.create(userDto);

        assertEquals(user, actualUser);
    }

    @Test
    void getAllUsers() {
        when(userService.getAll())
                .thenReturn(List.of(user));

        List<User> users = userController.findAllUser();

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void getUserById() {
        when(userService.getById(1L))
                .thenReturn(user);

        User actualUser = userController.findUser(1L);

        assertEquals(user, actualUser);
    }

    @Test
    void updateUser() {
        when(userService.update(userDto, 1L))
                .thenReturn(user);

        User actualUser = userController.update(1L, userDto);

        assertEquals(user, actualUser);
    }
}