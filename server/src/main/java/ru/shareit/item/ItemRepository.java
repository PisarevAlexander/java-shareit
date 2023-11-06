package ru.shareit.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Item repository
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Find item by id
     * @param itemId the item id
     * @return the optional of item
     */

    Optional<Item> findItemById(long itemId);

    /**
     * Find all by owner order by id
     * @param userId   the user id
     * @param pageable the pageable
     * @return the page of items
     */

    Page<Item> findAllByOwnerOrderById(long userId, Pageable pageable);

    /**
     * Find all by name containing ignore case or description containing ignore case and available true
     * @param name        the name
     * @param description the description
     * @param pageable    the pageable
     * @return the list of items
     */

    List<Item> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(String name,
                                                                                                  String description,
                                                                                                  Pageable pageable);

    /**
     * Find all by request id
     * @param requestId the request id
     * @return the list of items
     */

    List<Item> findAllByRequestId(long requestId);

    /**
     * Find all by request id in
     * @param requestId the request id
     * @return the list of items
     */

    List<Item> findAllByRequestIdIn(List<Long> requestId);
}