package ru.shareit.IT.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.shareit.user.User;
import ru.shareit.user.UserController;
import ru.shareit.user.UserDto;
import ru.shareit.user.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User controller integration test
 */

@ExtendWith(MockitoExtension.class)
class UserControllerIntegrationTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private UserDto userDto;
    private User user;

    /**
     * Sets up
     */

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();

        userDto = new UserDto("test", "test@gmail.com");
        user = new User(1, "test", "test@gmail.com");
    }

    /**
     * Create new user with valid user dto return user test
     */

    @Test
    void createNewUser_WithValidUserDto_ReturnUser() throws Exception {
        when(userService.create(userDto))
                .thenReturn(user);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    /**
     * Gets all users test
     */

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAll())
                .thenReturn(List.of(user));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(userDto.getName()))
                .andExpect(jsonPath("$[0].email").value(userDto.getEmail()));
    }

    /**
     * Gets user by id test
     */

    @Test
    void getUserById() throws Exception {
        when(userService.getById(anyLong()))
                .thenReturn(user);

        mvc.perform(get("/users/{userId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    /**
     * Update user test
     */

    @Test
    void updateUser() throws Exception {
        when(userService.update(any(), anyLong()))
                .thenReturn(user);

        mvc.perform(patch("/users/{userId}", "1")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    /**
     * Delete user test
     */

    @Test
    void deleteUser() throws Exception {
        mvc.perform(delete("/users/{userId}", "1"))
                .andExpect(status().isOk());
    }
}