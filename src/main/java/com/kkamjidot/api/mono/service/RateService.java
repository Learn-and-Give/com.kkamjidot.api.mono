package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Rate;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import com.kkamjidot.api.mono.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RateService {
    private final RateRepository rateRepository;

    @Transactional
    public void rateQuiz(Rate rate) {
        Optional<Rate> findRate = rateRepository.findByUserAndQuiz_id(rate.getUser(), rate.getQuiz().getId());
        if (findRate.isPresent()) findRate.get().update(rate);
        else rateRepository.save(rate);
    }

    public Integer countOfGood(Quiz quiz) {
        return rateRepository.countOfGood(quiz);
    }

    public RateValue didIrate(Quiz quiz, User user) {
        return rateRepository.findByQuizAndUser(quiz, user).map(Rate::getRate).orElse(null);
    }
}
