package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByChallDeletedDateNull();

    Optional<Challenge> findByIdAndChallDeletedDateNull(Long id);

    @Query("""
            select c from Challenge c
            where c.challStartDate <= :now and c.challEndDate >= :now and c.challDeletedDate is null""")
    List<Challenge> findAllByRunning(@Param("now") LocalDateTime now);
}