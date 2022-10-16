package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.*;
import com.kkamjidot.api.mono.dto.response.*;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizRepository;
import com.kkamjidot.api.mono.repository.SolveRepository;
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
    private final SolveRepository solveRepository;
    private final QuizQueryRepository quizQueryRepository;

    private final QuizService quizService;
    private final UserService userService;
    private final ChallengeService challengeService;
    private final RateService rateService;
    private final SolveService solveService;
    private final TakeAClassService takeAClassService;

    @Deprecated
    public QuizResponse readQuiz(Long quizId, User user) throws NoSuchElementException, UnauthorizedException {
//        if (!takeAClassRepository.existsByChall_IdAndUser_IdAndTcApplicationstatus(challengeId, user.getId(), ApplicationStatus.ACCEPTED))
//            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");

        Quiz quiz = quizService.findById(quizId);

        takeAClassService.checkCanReadChallengeByChallengeId(quiz.getChallengeId(), user.getId());

        Solve solve = solveService.findSolveOrElseEmpty(quiz, user);

        // 응답 객체 생성
        return QuizResponse.of(quiz, user, solve, rateService.countOfGood(quiz), rateService.didIRate(quiz, user));
    }

    public QuizContentResponse readQuizContent(Long quizId, Long userId) throws NoSuchElementException, UnauthorizedException {
        Quiz quiz = quizService.findById(quizId);
        User user = userService.findById(userId);

        takeAClassService.checkCanReadChallengeByChallengeId(quiz.getChallengeId(), userId);

        Solve solve = solveService.findSolveOrElseEmpty(quizId, userId);

        // 응답 객체 생성
        return QuizContentResponse.of(quiz, user, solve, rateService.countOfGood(quiz), solveService.numberOfQuizzesSolved(quizId), rateService.didIRate(quiz, user));
    }

    public QuizAnswerResponse readQuizAnswer(Long quizId, Long userId) {
        Quiz quiz = quizService.findById(quizId);
        Solve solve = solveService.findSolve(quizId, userId);

        // 응답 객체 생성
        return new QuizAnswerResponse(quiz, solve);
    }

    public MyQuizResponse readMyQuiz(Long quizId, User user) throws UnauthorizedException {
        Quiz quiz = quizService.findOneMine(quizId, user);
        return MyQuizResponse.of(quiz, rateService.countOfGood(quiz), rateService.didIRate(quiz, user));
    }

    public List<QuizSummaryResponse> readMyQuizzes(Integer week, User user, Long challengeId) {
        // 퀴즈 조회
        List<Quiz> quizzes;
        if (week == 0) quizzes = quizRepository.findByUserAndChallenge_IdOrderByQuizWeekAsc(user, challengeId);
        else quizzes = quizRepository.findByQuizWeekAndUserAndChallenge_Id(week, user, challengeId);

        // 응답 개체 생성
        return getQuizSummaryResponses(user, quizzes);
    }

    public QuizCountByChallengeResponse countMyQuizzes(Integer week, User user, Long challengeId) {
        int count;
        if (week == 0) count = quizRepository.countByUserAndChallenge_Id(user, challengeId);           // 제출한 모든 퀴즈 개수
        else count = quizRepository.countByQuizWeekAndUserAndChallenge_Id(week, user, challengeId);   // 주차별 제출한 퀴즈 개수

        return QuizCountByChallengeResponse.builder()
                .count(count)   // 퀴즈 개수 조회
                .challengeId(challengeId)
                .week(week == 0 ? null : week)
                .build();
    }

    public List<QuizTotalCountByWeekResponse> countMyTotalQuizzes(Long userId) {
        return quizQueryRepository.countQuizzesByUserId(userId);
    }

    public List<QuizSummaryResponse> readQuizSummaries(User user, Long challengeId, List<Integer> weeks) throws UnauthorizedException {
        takeAClassService.checkCanReadChallengeByChallengeId(challengeId, user.getId());

        List<Quiz> quizzes = quizQueryRepository.findByUserAndChallenge_IdAndQuizWeek(challengeId, weeks);

        // 응답 개체 생성
        return getQuizSummaryResponses(user, quizzes);
    }

    @NotNull
    private List<QuizSummaryResponse> getQuizSummaryResponses(User user, List<Quiz> quizzes) {
        List<QuizSummaryResponse> responses = new ArrayList<>(quizzes.size());
        for (Quiz quiz : quizzes) {
            responses.add(QuizSummaryResponse.of(quiz, user, solveService.findSolveOrElseEmpty(quiz, user), rateService.countOfGood(quiz), rateService.didIRate(quiz, user)));
        }
        return responses;
    }

    public Solve findSolve(Long quizId, Long userId) {
        return solveRepository.findByQuizIdAndUserId(quizId, userId).orElseThrow(() -> new UnauthorizedException("아직 풀지 않았습니다."));
    }

    public QuizSubmissionStatusResponse readQuizSubmissionStatus(User user, Long challengeId) {
        Challenge challenge = challengeService.findOne(challengeId);

        return null;
    }
}
