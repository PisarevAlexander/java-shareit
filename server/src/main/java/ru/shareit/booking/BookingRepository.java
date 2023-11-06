package ru.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Booking repository
 */

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Find booking by id
     * @param bookingId the booking id
     * @return the optional of booking
     */

    Optional<Booking> findBookingById(long bookingId);

    /**
     * Find all by booker id order by start desc
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list of booking
     */

    List<Booking> findAllByBooker_IdOrderByStartDesc(long userId, Pageable pageable);

    /**
     * Find all by booker id and end before order by start desc
     * @param userId the user id
     * @param date   the date
     * @return the list of booking
     */

    List<Booking> findAllByBooker_IdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime date);

    /**
     * Find all by booker id and start after order by start desc
     * @param userId the user id
     * @param date   the date
     * @return the list of booking
     */

    List<Booking> findAllByBooker_IdAndStartAfterOrderByStartDesc(long userId, LocalDateTime date);

    /**
     * Find all by booker id and start before and end after order by start desc
     * @param userId the user id
     * @param date1  the date 1
     * @param date2  the date 2
     * @return the list of booking
     */

    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime date1,
                                                                              LocalDateTime date2);

    /**
     * Find all by booker id and status order by start desc
     * @param userId the user id
     * @param status the status
     * @return the list of booking
     */

    List<Booking> findAllByBooker_IdAndStatusOrderByStartDesc(long userId, Status status);

    /**
     * Find all by item owner order by start desc
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list of booking
     */

    List<Booking> findAllByItem_OwnerOrderByStartDesc(long userId, Pageable pageable);

    /**
     * Find all by item owner and end before order by start desc
     * @param userId the user id
     * @param date   the date
     * @return the list of booking
     */

    List<Booking> findAllByItem_OwnerAndEndBeforeOrderByStartDesc(long userId, LocalDateTime date);

    /**
     * Find all by item owner and start after order by start desc
     * @param userId the user id
     * @param date   the date
     * @return the list of booking
     */

    List<Booking> findAllByItem_OwnerAndStartAfterOrderByStartDesc(long userId, LocalDateTime date);

    /**
     * Find all by item owner and start before and end after order by start desc
     * @param userId the user id
     * @param date1  the date 1
     * @param date2  the date 2
     * @return the list of booking
     */

    List<Booking> findAllByItem_OwnerAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime date1,
                                                                               LocalDateTime date2);

    /**
     * Find all by item owner and status order by start desc
     * @param userId the user id
     * @param status the status
     * @return the list of booking
     */

    List<Booking> findAllByItem_OwnerAndStatusOrderByStartDesc(long userId, Status status);

    /**
     * Find all by item id and item owner and status
     * @param itemId  the item id
     * @param ownerId the owner id
     * @param status  the status
     * @return the list of booking
     */

    List<Booking> findAllByItem_IdAndItem_OwnerAndStatus(long itemId, long ownerId, Status status);

    /**
     * Find all by item owner and status
     * @param userId the user id
     * @param status the status
     * @return the list of booking
     */

    List<Booking> findAllByItem_OwnerAndStatus(long userId, Status status);

    /**
     * Find booking by booker id and item id and end before
     * @param userId the user id
     * @param itemId the item id
     * @param date   the date
     * @return the list of booking
     */

    List<Booking> findBookingByBooker_IdAndItem_IdAndEndBefore(long userId, long itemId, LocalDateTime date);
}