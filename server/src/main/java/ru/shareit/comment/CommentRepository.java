package ru.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Comment repository
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Find all by item id
     * @param itemId the item id
     * @return the list of comments
     */

    List<Comment> findAllByItem_Id(long itemId);
}