package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findBookingById(long bookingId);

    List<Booking> findAllByBooker_IdOrderByStartDesc(long userId, Pageable pageable);

    List<Booking> findAllByBooker_IdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime date);

    List<Booking> findAllByBooker_IdAndStartAfterOrderByStartDesc(long userId, LocalDateTime date);

    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime date1,
                                                                              LocalDateTime date2);

    List<Booking> findAllByBooker_IdAndStatusOrderByStartDesc(long userId, Status status);

    List<Booking> findAllByItem_OwnerOrderByStartDesc(long userId, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndEndBeforeOrderByStartDesc(long userId, LocalDateTime date);

    List<Booking> findAllByItem_OwnerAndStartAfterOrderByStartDesc(long userId, LocalDateTime date);

    List<Booking> findAllByItem_OwnerAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime date1,
                                                                               LocalDateTime date2);

    List<Booking> findAllByItem_OwnerAndStatusOrderByStartDesc(long userId, Status status);

    List<Booking> findAllByItem_IdAndItem_OwnerAndStatus(long itemId, long ownerId, Status status);

    List<Booking> findAllByItem_OwnerAndStatus(long userId, Status status);

    List<Booking> findBookingByBooker_IdAndItem_IdAndAndEndBefore(long userId, long itemId, LocalDateTime date);
}
