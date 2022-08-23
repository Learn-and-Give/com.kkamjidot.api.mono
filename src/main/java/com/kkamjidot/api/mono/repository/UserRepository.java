package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndUserPasswordAndUserDeletedDateNull(String userName, String userPassword);

    Optional<User> findByUserPassword(String userPassword);
}