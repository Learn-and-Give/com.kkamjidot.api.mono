package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    Optional<Solve> findByQuizAndUser(Quiz quiz, User user);

    Optional<Solve> findByQuiz_IdAndUser_Id(Long quizId, Long userId);

    boolean existsByQuizAndUser(Quiz quiz, User user);

    boolean existsByQuiz_IdAndUser_Id(Long quizId, Long userId);
}