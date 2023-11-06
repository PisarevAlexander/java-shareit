package ru.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.shareit.client.BaseClient;
import ru.shareit.user.dto.UserDto;

/**
 * The type User client
 */

@Service
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    /**
     * Instantiates a new User client
     * @param serverUrl the server url
     * @param builder   the builder
     */

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * Create response entity
     * @param userDto the user dto
     * @return the response entity
     */

    public ResponseEntity<Object> create(UserDto userDto) {
        return post("", userDto);
    }

    /**
     * Find user response entity
     * @param userId the user id
     * @return the response entity
     */

    public ResponseEntity<Object> findUser(long userId) {
        return get("/" + userId);
    }

    /**
     * Find all user response entity
     * @return the response entity
     */

    public ResponseEntity<Object> findAllUser() {
        return get("");
    }

    /**
     * Update response entity
     * @param userId  the user id
     * @param userDto the user dto
     * @return the response entity
     */

    public ResponseEntity<Object> update(long userId, UserDto userDto) {
        return patch("/" + userId, userDto);
    }

    /**
     * Delete response entity
     * @param userId the user id
     * @return the response entity
     */

    public ResponseEntity<Object> delete(long userId) {
        return delete("/" + userId);
    }
}