package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        LocalDateTime time = LocalDateTime.now();
        booking = new Booking(1L, time, time.plusDays(10),
                new Item(), new User(), Status.APPROVED);
        bookingDto = new BookingDto(1L, time, time.plusDays(10));
    }

    @Test
    void create() {
        when(bookingService.create(bookingDto, 1L))
                .thenReturn(booking);

        Booking actualBooking = bookingController.create(bookingDto, 1L);

        assertEquals(booking, actualBooking);
    }

    @Test
    void update() {
        when(bookingService.update(1L, true, 1L))
                .thenReturn(booking);

        Booking actualBooking = bookingController.update(1L, true, 1L);

        assertEquals(booking, actualBooking);
    }

    @Test
    void findBooking() {
        when(bookingService.getById(1L, 1L))
                .thenReturn(booking);

        Booking actualBooking = bookingController.findBooking(1L, 1L);

        assertEquals(booking, actualBooking);
    }

    @Test
    void findAllUserBooking() {
        when(bookingService.getAllUserBooking(1L, "ALL", 0, 1))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingController.findAllUserBooking(1L, "ALL", 0, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void findAllOwnerBooking() {
        when(bookingService.getAllOwnerBooking(1L, "ALL", 0, 1))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingController.findAllOwnerBooking(1L, "ALL", 0, 1);

        assertEquals(List.of(booking), bookings);
    }
}