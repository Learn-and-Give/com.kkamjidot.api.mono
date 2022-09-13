package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import com.kkamjidot.api.mono.dto.response.QuizSummaryResponse;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizRepository;
import com.kkamjidot.api.mono.repository.RateRepository;
import com.kkamjidot.api.mono.service.RateService;
import com.kkamjidot.api.mono.service.SolveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RateQueryService {
    private final RateService rateService;
    private final SolveService solveService;

    public List<QuizSummaryResponse> readMyGoodQuizzes(User user, Challenge challenge) throws UnauthorizedException {
        List<Quiz> quizzes = rateService.findMyGoodQuizzes(user, challenge);

        // 응답 개체 생성
        List<QuizSummaryResponse> responses = new ArrayList<>(quizzes.size());
        for (Quiz quiz : quizzes) {
            responses.add(QuizSummaryResponse.of(quiz, user, solveService.findSolveOrElseEmpty(quiz, user), rateService.countOfGood(quiz), RateValue.GOOD));
        }
        return responses;
    }
}
