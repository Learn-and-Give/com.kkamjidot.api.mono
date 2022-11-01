package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TakeAClassRepository extends JpaRepository<TakeAClass, Long> {
    Optional<TakeAClass> findByChallAndUser(Challenge chall, User user);
    Boolean existsByChall_IdAndUser_IdAndTcApplicationstatus(Long challengeId, Long userId, ApplicationStatus applicationStatus);

    Optional<TakeAClass> findByTcApplicationstatusAndChallIdAndUserId(ApplicationStatus status, Long challengeId, Long userId);

    List<TakeAClass> findByUserId(Long userID);
}