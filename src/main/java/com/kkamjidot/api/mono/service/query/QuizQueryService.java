package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.*;
import com.kkamjidot.api.mono.dto.response.*;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizRepository;
import com.kkamjidot.api.mono.repository.query.QuizQueryRepository;
import com.kkamjidot.api.mono.service.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizQueryService {
    private final QuizRepository quizRepository;
    private final QuizQueryRepository quizQueryRepository;

    private final QuizService quizService;
    private final UserService userService;
    private final RateService rateService;
    private final SolveService solveService;
    private final TakeAClassService takeAClassService;

    public QuizContentResponse readQuizContent(Long quizId, Long userId) throws NoSuchElementException, UnauthorizedException {
        Quiz quiz = quizService.findById(quizId);
        User user = userService.findById(userId);

        takeAClassService.checkCanReadChallengeByChallengeId(quiz.getChallengeId(), userId);

        Solve solve = solveService.findSolveOrElseEmpty(userId, quizId);

        // 응답 객체 생성
        return QuizContentResponse.of(quiz, user, solve,
                rateService.countOfGood(quizId),
                solveService.numberOfQuizzesSolved(quizId),
                rateService.didIRate(userId, quizId));
    }

    public QuizAnswerResponse readQuizAnswer(Long quizId, Long userId) {
        Quiz quiz = quizService.findById(quizId);
        Solve solve = solveService.findSolve(userId, quizId);

        // 응답 객체 생성
        return new QuizAnswerResponse(quiz, solve);
    }

    public MyQuizResponse readMyQuiz(Long quizId, Long userId) throws UnauthorizedException {
        Quiz quiz = quizService.findOneMine(quizId, userId);
        return MyQuizResponse.of(
                quiz,
                rateService.countOfGood(quizId),
                solveService.numberOfQuizzesSolved(quizId),
                rateService.didIRate(userId, quizId));
    }

    public List<QuizSummaryResponse> readMyQuizzes(Integer week, Long userId, Long challengeId) {
        // 퀴즈 조회
        List<Quiz> quizzes;
        if (week == 0) quizzes = quizRepository.findByUserIdAndChallenge_IdOrderByQuizWeekAsc(userId, challengeId);
        else quizzes = quizRepository.findByQuizWeekAndUserIdAndChallenge_Id(week, userId, challengeId);

        // 응답 개체 생성
        return getQuizSummaryResponses(userId, quizzes);
    }

    public QuizCountByChallengeResponse countMyQuizzes(Integer week, Long userId, Long challengeId) {
        int count;
        if (week == 0) count = quizRepository.countByUserIdAndChallenge_Id(userId, challengeId);           // 제출한 모든 퀴즈 개수
        else count = quizRepository.countByQuizWeekAndUserIdAndChallenge_Id(week, userId, challengeId);   // 주차별 제출한 퀴즈 개수

        return QuizCountByChallengeResponse.builder()
                .count(count)   // 퀴즈 개수 조회
                .challengeId(challengeId)
                .week(week == 0 ? null : week)
                .build();
    }

    public List<QuizTotalCountByWeekResponse> countMyTotalQuizzes(Long userId) {
        return quizQueryRepository.countQuizzesByUserId(userId);
    }

    public List<QuizSummaryResponse> readQuizSummaries(Long userId, Long challengeId, List<Integer> weeks) throws UnauthorizedException {
        takeAClassService.checkCanReadChallengeByChallengeId(challengeId, userId);

        List<Quiz> quizzes = quizQueryRepository.findByUserAndChallenge_IdAndQuizWeek(challengeId, weeks);

        // 응답 개체 생성
        return getQuizSummaryResponses(userId, quizzes);
    }

    @NotNull
    private List<QuizSummaryResponse> getQuizSummaryResponses(Long userId, List<Quiz> quizzes) {
        User user = userService.findById(userId);

        List<QuizSummaryResponse> responses = new ArrayList<>(quizzes.size());
        for (Quiz quiz : quizzes) {
            responses.add(QuizSummaryResponse.of(
                    quiz,
                    user,
                    solveService.findSolveOrElseEmpty(userId, quiz.getId()),
                    rateService.countOfGood(quiz.getId()),
                    solveService.numberOfQuizzesSolved(quiz.getId()),
                    rateService.didIRate(userId, quiz.getId())
            ));
        }
        return responses;
    }
}
