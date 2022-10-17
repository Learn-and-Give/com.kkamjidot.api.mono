package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    Optional<Solve> findByQuizAndUser(Quiz quiz, User user);

    Optional<Solve> findByQuizIdAndUserId(Long quizId, Long userId);

    boolean existsByQuizAndUser(Quiz quiz, User user);

    boolean existsByQuizIdAndUserId(Long quizId, Long userId);

    Integer countByQuizId(Long quizId);

    @Query("select count(s) from Solve s where s.quiz.user.id = ?1")
    Long countOfAllQuizzesSolvedByUser(Long id);
}