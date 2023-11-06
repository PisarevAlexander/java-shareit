package ru.shareit.IT.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.shareit.OffsetBasedPageRequest;
import ru.shareit.booking.Booking;
import ru.shareit.booking.BookingRepository;
import ru.shareit.booking.Status;
import ru.shareit.item.Item;
import ru.shareit.item.ItemRepository;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Booking repository test
 */

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    private Booking booking;
    private User user2;
    private LocalDateTime time;

    /**
     * Add user
     */

    @BeforeEach
    public void addUser() {
        time = LocalDateTime.now();
        User user1 = new User(1L, "test", "test@gmail.com");
        user1 = userRepository.save(user1);
        user2 = new User(2L, "test1", "test1@gmail.com");
        user2 = userRepository.save(user2);
        Item item = new Item(1L, "test", "description", true, user1.getId(), null);
        item = itemRepository.save(item);
        booking = new Booking(1L, time.plusDays(1), time.plusDays(10), item, user2, Status.APPROVED);
        booking = bookingRepository.save(booking);
    }

    /**
     * Delete repository
     */

    @AfterEach
    public void delete() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    /**
     * Find booking by id test
     */

    @Test
    void findBookingById() {
        Optional<Booking> actualBooking = bookingRepository.findBookingById(booking.getId());

        assertTrue(actualBooking.isPresent());
    }

    /**
     * Find all by booker id order by start desc test
     */

    @Test
    void findAllByBooker_IdOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findAllByBooker_IdOrderByStartDesc(user2.getId(),
                new OffsetBasedPageRequest(0, 1));

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by booker id and end before order by start desc test
     */

    @Test
    void findAllByBooker_IdAndEndBeforeOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(user2.getId(),
                time.plusMonths(2));

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by booker id and start after order by start desc test
     */

    @Test
    void findAllByBooker_IdAndStartAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findAllByBooker_IdAndStartAfterOrderByStartDesc(user2.getId(),
                time.minusDays(25));

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by booker id and start before and end after order by start desc test
     */

    @Test
    void findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(user2.getId(),
                time.plusDays(25), time);

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by booker id and status order by start desc test
     */

    @Test
    void findAllByBooker_IdAndStatusOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findAllByBooker_IdAndStatusOrderByStartDesc(user2.getId(),
                Status.APPROVED);

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item owner order by start desc test
     */

    @Test
    void findAllByItem_OwnerOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findAllByItem_OwnerOrderByStartDesc(booking.getItem().getOwner(),
                new OffsetBasedPageRequest(0, 1));

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item owner and end before order by start desc test
     */

    @Test
    void findAllByItem_OwnerAndEndBeforeOrderByStartDesc() {
        List<Booking> bookings = bookingRepository
                .findAllByItem_OwnerAndEndBeforeOrderByStartDesc(booking.getItem().getOwner(), time.plusMonths(2));

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item owner and start after order by start desc test
     */

    @Test
    void findAllByItem_OwnerAndStartAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository
                .findAllByItem_OwnerAndStartAfterOrderByStartDesc(booking.getItem().getOwner(), time.minusDays(25));

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item owner and start before and end after order by start desc test
     */

    @Test
    void findAllByItem_OwnerAndStartBeforeAndEndAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository
                .findAllByItem_OwnerAndStartBeforeAndEndAfterOrderByStartDesc(booking.getItem().getOwner(),
                        time.plusDays(5), time);

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item owner and status order by start desc test
     */

    @Test
    void findAllByItem_OwnerAndStatusOrderByStartDesc() {
        List<Booking> bookings = bookingRepository
                .findAllByItem_OwnerAndStatusOrderByStartDesc(booking.getItem().getOwner(), Status.APPROVED);

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item id and item owner and status test
     */

    @Test
    void findAllByItem_IdAndItem_OwnerAndStatus() {
        List<Booking> bookings = bookingRepository.findAllByItem_IdAndItem_OwnerAndStatus(booking.getItem().getId(),
                booking.getItem().getOwner(), Status.APPROVED);

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find all by item owner and status test
     */

    @Test
    void findAllByItem_OwnerAndStatus() {
        List<Booking> bookings = bookingRepository.findAllByItem_OwnerAndStatus(booking.getItem().getOwner(),
                Status.APPROVED);

        assertEquals(List.of(booking), bookings);
    }

    /**
     * Find booking by booker id and item id and end before test
     */

    @Test
    void findBookingByBooker_IdAndItem_IdAndAndEndBefore() {
        List<Booking> bookings = bookingRepository
                .findBookingByBooker_IdAndItem_IdAndEndBefore(booking.getBooker().getId(),
                        booking.getItem().getId(), time.plusMonths(1));

        assertEquals(List.of(booking), bookings);
    }
}