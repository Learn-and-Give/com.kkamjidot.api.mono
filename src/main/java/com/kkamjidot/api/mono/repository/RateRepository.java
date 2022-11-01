package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query("select count(r) from Rate r where r.quiz.id = :quizId and upper(r.rate) = 'GOOD'")
    Integer countOfGood(@Param("quizId") Long quizId);

    Optional<Rate> findByUserIdAndQuizId(Long userId, Long quizId);

    @Query("select r.quiz from Rate r where r.user.id = :userId and r.rate = 'GOOD' and r.quiz.challenge.id = :challengeId")
    List<Quiz> findMyGoodQuizzes(@Param("userId") Long userId, @Param("challengeId") Long challengeId);
}