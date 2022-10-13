package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndUserPasswordAndUserDeletedDateNull(String userName, String userPassword);

    Optional<User> findByUserPassword(String userPassword);

    @Query("select u from User u where u.userUniversityEmail = :email or u.userSecondEmail = :email")
    Optional<User> findByEmail(@Param("email") String email);
}