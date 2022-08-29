package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByIdAndQuizDeletedDateNull(Long id);

    Optional<Quiz> findByIdAndUserAndQuizDeletedDateNull(Long id, User user);

    int countByQuizWeekAndUserAndChallenge_Id(Integer quizWeek, User user, Long challengeId);

    int countByUserAndChallenge_Id(User user, Long challengeId);
}