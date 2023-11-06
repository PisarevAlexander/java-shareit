package ru.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by id
     * @param userId the user id
     * @return the optional of user
     */

    Optional<User> findUserById(long userId);
}