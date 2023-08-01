package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByUserOrderByCreatedDesc(long userId);

    Optional<ItemRequest> findById(long requestId);

    Page<ItemRequest> findAllByUserNot(long userId, Pageable pageable);
}