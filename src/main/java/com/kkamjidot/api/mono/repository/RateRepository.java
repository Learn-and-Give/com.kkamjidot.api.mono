package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Rate;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query("select count(r) from Rate r where r.quiz = ?1 and upper(r.rate) = 'GOOD'")
    Integer countOfGood(Quiz quiz);

    Optional<Rate> findByUserAndQuiz_id(User user, Long id);

    Optional<Rate> findByQuizAndUser(Quiz quiz, User user);

    @Query("select r.quiz from Rate r where r.user = :user and r.rate = 'GOOD' and r.quiz.challenge = :challenge")
    List<Quiz> findMyGoodQuizzes(@Param("user") User user, @Param("challenge") Challenge challenge);
}