package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerIntegrationTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private Booking booking;
    private BookingDto bookingDto;
    private LocalDateTime time;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .build();

        time = LocalDateTime.now();
        booking = new Booking(1L, time.plusDays(1), time.plusDays(10),
                new Item(1L, "test", "description", true, 1L, 1L),
                new User(1, "test", "test@gmail.com"), Status.APPROVED);
        bookingDto = new BookingDto(1L, time.plusDays(1), time.plusDays(10));
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createNewBooking_WhenValidBookingDto_ReturnBooking() throws Exception {
        when(bookingService.create(bookingDto, 1L))
                .thenReturn(booking);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

 /*   @Test
    void update() {
    }

    @Test
    void findBooking() {
    }

    @Test
    void findAllUserBooking() {
    }

    @Test
    void findAllOwnerBooking() {
    }*/
}