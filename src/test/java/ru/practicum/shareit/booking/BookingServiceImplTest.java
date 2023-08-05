package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    public BookingRepository bookingRepository;

    @Mock
    public UserService userService;

    @Mock
    public ItemService itemService;

    private Booking booking;
    private BookingDto bookingDto;
    private User user2;
    private Item item;
    private LocalDateTime time;

    @BeforeEach
    public void setUp() {
        time = LocalDateTime.now();
        User user1 = new User(1L, "test", "test@gmail.com");
        user2 = new User(2L, "test1", "test1@gmail.com");
        item = new Item(1L, "test", "description", true, user1.getId(), null);
        booking = new Booking(null, time.plusDays(1), time.plusDays(10), item, user2, Status.WAITING);
        bookingDto = new BookingDto(1L, time.plusDays(1), time.plusDays(10));
    }

    @Test
    void create_WhereAllValid_ReturnBooking() {
        when(userService.getById(1L))
                .thenReturn(user2);
        when(itemService.getItemById(1L))
                .thenReturn(item);
        when(bookingRepository.save(booking))
                .thenReturn(booking);

        Booking actualBooking = bookingService.create(bookingDto, 1L);
        booking.setStatus(Status.WAITING);

        assertEquals(booking, actualBooking);
    }

    @Test
    void create_WhenUserIdNotEqualsOwner_ReturnNotFoundException() {
        item.setOwner(2L);
        when(userService.getById(2L))
                .thenReturn(user2);
        when(itemService.getItemById(1L))
                .thenReturn(item);

        assertThrows(NotFoundException.class, () -> bookingService.create(bookingDto, 2L));
        verify((bookingRepository), never()).save(booking);
    }

    @Test
    void create_WhenItemNotAvailable_ReturnNotRequestException() {
        item.setAvailable(false);
        when(userService.getById(1L))
                .thenReturn(user2);
        when(itemService.getItemById(1L))
                .thenReturn(item);

        assertThrows(BadRequestException.class, () -> bookingService.create(bookingDto, 1L));
        verify((bookingRepository), never()).save(booking);
    }

    @Test
    void update() {
        when(userService.getById(1L))
                .thenReturn(user2);
        when(bookingRepository.findBookingById(1L))
                .thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking))
                .thenReturn(booking);

        Booking actualBooking = bookingService.update(1L, true, 1L);

        assertEquals(booking, actualBooking);
    }

    @Test
    void getById() {
        when(bookingRepository.findBookingById(1L))
                .thenReturn(Optional.of(booking));

        Booking actualBooking = bookingService.getById(1L, 1L);

        assertEquals(booking, actualBooking);
    }

    @Test
    void getAllUserBooking_WhereStateWaiting_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByBooker_IdAndStatusOrderByStartDesc(2L, Status.valueOf("WAITING")))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllUserBooking(2L, "WAITING", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllUserBooking_WhereStateALL_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByBooker_IdOrderByStartDesc(anyLong(), any(Pageable.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllUserBooking(2L, "ALL", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllUserBooking_WhereStateCURRENT_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(),
                        any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllUserBooking(2L, "CURRENT", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllUserBooking_WhereStatePAST_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByBooker_IdAndEndBeforeOrderByStartDesc(anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllUserBooking(2L, "PAST", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllUserBooking_WhereStateFUTURE_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByBooker_IdAndStartAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllUserBooking(2L, "FUTURE", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllUserBooking_WhereStateRejected_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByBooker_IdAndStatusOrderByStartDesc(2L, Status.valueOf("REJECTED")))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllUserBooking(2L, "REJECTED", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllUserBooking_WhereStateNotValid_ReturnException() {
        when(userService.getById(2L))
                .thenReturn(user2);

        assertThrows(BadRequestException.class, () -> bookingService.getAllUserBooking(2L, "asdasd", 1, 1));
    }

    @Test
    void getAllOwnerBooking_WhereStateWaiting_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByItem_OwnerAndStatusOrderByStartDesc(2L, Status.valueOf("WAITING")))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllOwnerBooking(2L, "WAITING", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllOwnerBooking_WhereStateAll_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByItem_OwnerOrderByStartDesc(anyLong(), any(Pageable.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllOwnerBooking(2L, "ALL", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllOwnerBooking_WhereStateCURRENT_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByItem_OwnerAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(),
                        any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllOwnerBooking(2L, "CURRENT", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllOwnerBooking_WhereStatePAST_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByItem_OwnerAndEndBeforeOrderByStartDesc(anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllOwnerBooking(2L, "PAST", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllOwnerBooking_WhereStateFUTURE_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByItem_OwnerAndStartAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllOwnerBooking(2L, "FUTURE", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllOwnerBooking_WhereStateRejected_ReturnListOfBooking() {
        when(userService.getById(2L))
                .thenReturn(user2);
        when(bookingRepository
                .findAllByItem_OwnerAndStatusOrderByStartDesc(2L, Status.valueOf("REJECTED")))
                .thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllOwnerBooking(2L, "REJECTED", 1, 1);

        assertEquals(List.of(booking), bookings);
    }

    @Test
    void getAllOwnerBooking_WhereStateNotValid_ReturnException() {
        when(userService.getById(2L))
                .thenReturn(user2);

        assertThrows(BadRequestException.class, () -> bookingService.getAllOwnerBooking(2L, "asdasd", 1, 1));
    }
}