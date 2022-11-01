package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import com.kkamjidot.api.mono.dto.response.QuizSummaryResponse;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.service.RateService;
import com.kkamjidot.api.mono.service.SolveService;
import com.kkamjidot.api.mono.service.UserService;
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
    private final UserService userService;

    public List<QuizSummaryResponse> readMyGoodQuizzes(Long userId, Long challengeId) throws UnauthorizedException {
        List<Quiz> quizzes = rateService.findMyGoodQuizzes(userId, challengeId);

        // 응답 개체 생성
        User user = userService.findById(userId);
        List<QuizSummaryResponse> responses = new ArrayList<>(quizzes.size());
        for (Quiz quiz : quizzes) {
            responses.add(QuizSummaryResponse.of(quiz, user,
                    solveService.findSolveOrElseEmpty(userId, quiz.getId()),
                    rateService.countOfGood(quiz.getId()),
                    solveService.numberOfQuizzesSolved(quiz.getId()),
                    RateValue.GOOD));
        }
        return responses;
    }
}
