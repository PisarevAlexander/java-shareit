package ru.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.shareit.client.BaseClient;
import ru.shareit.request.dto.RequestDto;

import java.util.Map;

/**
 * The type Request client
 */

@Service
public class RequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    /**
     * Instantiates a new Request client
     * @param serverUrl the server url
     * @param builder   the builder
     */

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * Create response entity
     * @param requestDto the request dto
     * @param userId     the user id
     * @return the response entity
     */

    public ResponseEntity<Object> create(RequestDto requestDto, long userId) {
        return post("", userId, requestDto);
    }

    /**
     * Gets all
     * @param userId the user id
     * @return the all
     */

    public ResponseEntity<Object> getAll(long userId) {
        return get("", userId);
    }

    /**
     * Find item response entity
     * @param userId the user id
     * @param from   the from
     * @param size   the size
     * @return the response entity
     */

    public ResponseEntity<Object> findItem(long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all?from={from}&size={size}", userId, parameters);
    }

    /**
     * Find request by id response entity
     * @param requestId the request id
     * @param userId    the user id
     * @return the response entity
     */

    public ResponseEntity<Object> findRequestById(long requestId, long userId) {
        return get("/" + requestId, userId);
    }
}