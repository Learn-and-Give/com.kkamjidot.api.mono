package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByIdAndQuizDeletedDateNull(Long id);

    Optional<Quiz> findByIdAndUserAndQuizDeletedDateNull(Long id, User user);

    int countByQuizWeekAndUserAndChallenge_Id(Integer quizWeek, User user, Long challengeId);

    int countByUserAndChallenge_Id(User user, Long challengeId);

    List<Quiz> findByQuizWeekAndUserAndChallenge_Id(Integer quizWeek, User user, Long id);

    List<Quiz> findByUserAndChallenge_IdOrderByQuizWeekAsc(User user, Long id);

    int countByChallengeAndUserAndQuizWeekAndQuizDeletedDateNull(Challenge challenge, User user, Integer quizWeek);
}