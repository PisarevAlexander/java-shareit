package ru.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Item request repository
 */

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    /**
     * Find all by user order by created desc list
     * @param userId the user id
     * @return the list of requests
     */

    List<ItemRequest> findAllByUserOrderByCreatedDesc(long userId);

    /**
     * Find by id
     * @param requestId the request id
     * @return the optional of request
     */

    Optional<ItemRequest> findById(long requestId);

    /**
     * Find all by user
     * @param userId   the user id
     * @param pageable the pageable
     * @return the page of request
     */

    Page<ItemRequest> findAllByUserNot(long userId, Pageable pageable);
}