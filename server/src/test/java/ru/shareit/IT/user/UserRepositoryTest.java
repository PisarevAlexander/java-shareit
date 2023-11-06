package ru.shareit.IT.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * User repository test
 */

@DataJpaTest
class UserRepositoryTest {

    private User user;

    @Autowired
    private UserRepository userRepository;

    /**
     * Add user
     */

    @BeforeEach
    public void addUser() {
        user = new User(1, "test", "test@gmail.com");
        user = userRepository.save(user);
    }

    /**
     * Delete test
     */

    @AfterEach
    public void delete() {
        userRepository.deleteAll();
    }

    /**
     * Context loads test
     */

    @Test
    void contextLoads() {
       Optional<User> testUser = userRepository.findUserById(user.getId());

        assertTrue(testUser.isPresent());
    }
}