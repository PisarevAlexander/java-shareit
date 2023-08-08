package ru.practicum.shareit.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemById(long itemId);

    List<Item> findAllByOwner(long userId, Pageable pageable);

    List<Item> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(String name,
                                                                                                  String description,
                                                                                                  Pageable pageable);

    List<Item> findAllByRequestId(long requestId);

    List<Item> findAllByRequestIdIn(List<Long> requestId);
}