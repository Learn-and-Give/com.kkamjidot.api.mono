package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    Optional<Solve> findByQuizAndUser(Quiz quiz, User user);

    Optional<Solve> findByQuiz_IdAndUser_IdAndSolveAnswerNotNull(Long quizId, Long userId);

    Optional<Solve> findByQuiz_IdAndUserAndSolveAnswerNotNull(Long quizId, User user);

    Optional<Solve> findByQuiz_IdAndUserAndSolveAnswerNotNullAndSolveScoreNotNull(Long quizId, User user);

    boolean existsByQuizAndUser(Quiz quiz, User user);

}