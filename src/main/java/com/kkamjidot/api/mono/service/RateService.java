package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Rate;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import com.kkamjidot.api.mono.dto.request.QuizRateRequest;
import com.kkamjidot.api.mono.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RateService {
    private final RateRepository rateRepository;
    private final UserService userService;
    private final QuizService quizService;

    @Transactional
    public void rateQuiz(QuizRateRequest quizRateRequest,Long userId, Long quizId) {
        User user = userService.findById(userId);
        Quiz quiz = quizService.findById(quizId);

        Rate rate = Rate.builder()
                .rate(quizRateRequest.getRate())
                .user(user)
                .quiz(quiz)
                .build();

        Optional<Rate> findRate = rateRepository.findByUserIdAndQuizId(rate.getUser().getId(), rate.getQuiz().getId());
        if (findRate.isPresent()) findRate.get().update(rate);
        else rateRepository.save(rate);
    }

    public Integer countOfGood(Long quizId) {
        return rateRepository.countOfGood(quizId);
    }

    public RateValue didIRate(Long userId, Long quizId) {
        return rateRepository.findByUserIdAndQuizId(userId, quizId).map(Rate::getRate).orElse(null);
    }

    public List<Quiz> findMyGoodQuizzes(Long userId, Long challengeId) {
        return rateRepository.findMyGoodQuizzes(userId, challengeId);
    }
}
