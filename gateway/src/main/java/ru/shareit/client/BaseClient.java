package ru.shareit.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * The type Base client
 */

public class BaseClient {
    protected final RestTemplate rest;

    /**
     * Instantiates a new Base client
     * @param rest the rest
     */

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    /**
     * Get response entity.
     * @param path the path
     * @return the response entity
     */

    protected ResponseEntity<Object> get(String path) {
        return get(path, null, null);
    }

    /**
     * Get response entity
     * @param path   the path
     * @param userId the user id
     * @return the response entity
     */

    protected ResponseEntity<Object> get(String path, long userId) {
        return get(path, userId, null);
    }

    /**
     * Get response entity
     * @param path       the path
     * @param userId     the user id
     * @param parameters the parameters
     * @return the response entity
     */

    protected ResponseEntity<Object> get(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, parameters, null);
    }

    /**
     * Post response entity
     * @param <T>  the type parameter
     * @param path the path
     * @param body the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body);
    }

    /**
     * Post response entity
     * @param <T>    the type parameter
     * @param path   the path
     * @param userId the user id
     * @param body   the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> post(String path, long userId, T body) {
        return post(path, userId, null, body);
    }

    /**
     * Post response entity
     * @param <T>        the type parameter
     * @param path       the path
     * @param userId     the user id
     * @param parameters the parameters
     * @param body       the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> post(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, parameters, body);
    }

    /**
     * Put response entity
     * @param <T>    the type parameter
     * @param path   the path
     * @param userId the user id
     * @param body   the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> put(String path, long userId, T body) {
        return put(path, userId, null, body);
    }

    /**
     * Put response entity
     * @param <T>        the type parameter
     * @param path       the path
     * @param userId     the user id
     * @param parameters the parameters
     * @param body       the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> put(String path, long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.PUT, path, userId, parameters, body);
    }

    /**
     * Patch response entity
     * @param <T>  the type parameter
     * @param path the path
     * @param body the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> patch(String path, T body) {
        return patch(path, null, null, body);
    }

    /**
     * Patch response entity
     * @param <T>    the type parameter
     * @param path   the path
     * @param userId the user id
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> patch(String path, long userId) {
        return patch(path, userId, null, null);
    }

    /**
     * Patch response entity
     * @param <T>    the type parameter
     * @param path   the path
     * @param userId the user id
     * @param body   the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> patch(String path, long userId, T body) {
        return patch(path, userId, null, body);
    }

    /**
     * Patch response entity
     * @param <T>        the type parameter
     * @param path       the path
     * @param userId     the user id
     * @param parameters the parameters
     * @param body       the body
     * @return the response entity
     */

    protected <T> ResponseEntity<Object> patch(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, parameters, body);
    }

    /**
     * Delete response entity
     * @param path the path
     * @return the response entity
     */

    protected ResponseEntity<Object> delete(String path) {
        return delete(path, null, null);
    }

    /**
     * Delete response entity
     * @param path   the path
     * @param userId the user id
     * @return the response entity
     */

    protected ResponseEntity<Object> delete(String path, long userId) {
        return delete(path, userId, null);
    }

    /**
     * Delete response entity
     * @param path       the path
     * @param userId     the user id
     * @param parameters the parameters
     * @return the response entity
     */

    protected ResponseEntity<Object> delete(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.DELETE, path, userId, parameters, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, Long userId, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(userId));

        ResponseEntity<Object> shareitServerResponse;
        try {
            if (parameters != null) {
                shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(shareitServerResponse);
    }

    private HttpHeaders defaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}