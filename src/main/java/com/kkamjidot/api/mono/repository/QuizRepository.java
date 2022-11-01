package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByIdAndQuizDeletedDateNull(Long id);

    Optional<Quiz> findByIdAndUserIdAndQuizDeletedDateNull(Long quizId, Long userId);

    int countByQuizWeekAndUserIdAndChallenge_Id(Integer quizWeek, Long userId, Long challengeId);

    int countByUserIdAndChallenge_Id(Long userId, Long challengeId);

    List<Quiz> findByQuizWeekAndUserIdAndChallenge_Id(Integer quizWeek, Long userId, Long challengeId);

    List<Quiz> findByUserIdAndChallenge_IdOrderByQuizWeekAsc(Long userId, Long challengeId);

    int countByChallenge_IdAndUserIdAndQuizWeekAndQuizDeletedDateNull(Long challengeId, Long userId, Integer quizWeek);
}