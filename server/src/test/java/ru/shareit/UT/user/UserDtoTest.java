package ru.shareit.UT.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.shareit.user.UserDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * User dto test
 */

@JsonTest
class UserDtoTest {

    @Autowired
    private JacksonTester<UserDto> json;

    /**
     * Test user dto test
     */

    @Test
    void testUserDto() throws Exception {
        UserDto userDto = new UserDto("test", "test@gmail.com");

        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("test");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("test@gmail.com");
    }
}