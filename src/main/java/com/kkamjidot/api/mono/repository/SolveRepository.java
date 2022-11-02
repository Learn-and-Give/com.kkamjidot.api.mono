package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Solve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    Optional<Solve> findByUserIdAndQuizId(Long userId, Long quizId);

    boolean existsByUserIdAndQuizId(Long userId, Long quizId);

    Integer countByQuizId(Long quizId);

    @Query("select count(s) from Solve s where s.quiz.user.id = ?1")
    Long countOfAllQuizzesSolvedByUser(Long id);
}