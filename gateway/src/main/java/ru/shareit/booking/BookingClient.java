package ru.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.shareit.booking.dto.BookItemRequestDto;
import ru.shareit.booking.dto.BookingState;
import ru.shareit.client.BaseClient;

import java.util.Map;

/**
 * The type Booking client
 */

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    /**
     * Instantiates a new Booking client.
     * @param serverUrl the server url
     * @param builder   the builder
     */

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * Get bookings
     * @param userId the user id
     * @param state  the state
     * @param from   the from
     * @param size   the size
     * @return the bookings
     */

    public ResponseEntity<Object> getBookings(long userId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    /**
     * Book item response entity
     * @param userId     the user id
     * @param requestDto the request dto
     * @return the response entity
     */

    public ResponseEntity<Object> bookItem(long userId, BookItemRequestDto requestDto) {
        return post("", userId, requestDto);
    }

    /**
     * Get booking
     * @param userId    the user id
     * @param bookingId the booking id
     * @return the booking
     */

    public ResponseEntity<Object> getBooking(long userId, long bookingId) {
        return get("/" + bookingId, userId);
    }

    /**
     * Update response entity
     * @param bookingId the booking id
     * @param userId    the user id
     * @param approved  the approved
     * @return the response entity
     */

    public ResponseEntity<Object> update(long bookingId, long userId, boolean approved) {
        Map<String, Object> parameters = Map.of(
                "approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);
    }

    /**
     * Get all owner booking
     * @param userId the user id
     * @param state  the state
     * @param from   the from
     * @param size   the size
     * @return the all owner booking
     */

    public ResponseEntity<Object> getAllOwnerBooking(long userId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }
}