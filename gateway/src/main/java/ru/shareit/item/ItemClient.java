package ru.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.shareit.client.BaseClient;
import ru.shareit.comment.CommentDto;
import ru.shareit.item.dto.ItemDto;

import java.util.Map;

/**
 * The type Item client
 */

@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    /**
     * Instantiates a new Item client
     * @param serverUrl the server url
     * @param builder   the builder
     */

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * Create response entity
     * @param itemDto the item dto
     * @param userId  the user id
     * @return the response entity
     */

    public ResponseEntity<Object> create(ItemDto itemDto, long userId) {
        return post("", userId, itemDto);
    }

    /**
     * Update response entity
     * @param itemDto the item dto
     * @param itemId  the item id
     * @param userId  the user id
     * @return the response entity
     */

    public ResponseEntity<Object> update(ItemDto itemDto, long itemId, long userId) {
        return patch("/" + itemId, userId, itemDto);
    }

    /**
     * Find item response entity
     * @param itemId the item id
     * @param userID the user id
     * @return the response entity
     */

    public ResponseEntity<Object> findItem(long itemId, long userID) {
        return get("/" + itemId, userID);
    }

    /**
     * Find all item response entity
     * @param userId the user id
     * @param from   the from
     * @param size   the size
     * @return the response entity
     */

    public ResponseEntity<Object> findAllItem(long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", userId, parameters);
    }

    /**
     * Find item by text response entity
     * @param text the text
     * @param from the from
     * @param size the size
     * @return the response entity
     */

    public ResponseEntity<Object> findItemByText(String text, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "from", from,
                "size", size
        );
        return get("/search?text={text}&from={from}&size={size}", null, parameters);
    }

    /**
     * Save comment response entity
     * @param commentDto the comment dto
     * @param itemId     the item id
     * @param userId     the user id
     * @return the response entity
     */

    public ResponseEntity<Object> saveComment(CommentDto commentDto, long itemId, long userId) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }
}