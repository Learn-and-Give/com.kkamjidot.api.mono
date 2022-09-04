package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TakeAClassRepository extends JpaRepository<TakeAClass, Long> {
    Optional<TakeAClass> findByChallAndUser(Challenge chall, User user);

    Optional<TakeAClass> findByTcApplicationstatusAndChall_IdAndUser(ApplicationStatus status, Long challengeId, User user);

    List<TakeAClass> findByUser(User user);
}