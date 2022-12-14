package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Complete;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteRepository extends JpaRepository<Complete, Long> {
    List<Complete> findByUserAndChall(User user, Challenge chall);
    boolean existsByWeekAndUserIdAndChallId(Integer week, Long userId, Long challengeId);
    int countByChallAndWeek(Challenge challenge, Integer week);
}