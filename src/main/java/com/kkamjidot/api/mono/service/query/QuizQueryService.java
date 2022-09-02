package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.response.*;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizRepository;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import com.kkamjidot.api.mono.repository.SolveRepository;
import com.kkamjidot.api.mono.service.QuizService;
import lombok.RequiredArgsConstructor;
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
    private final ReadableRepository readableRepository;
    private final QuizService quizService;

    public QuizContentResponse readQuizContent(Long quizId, User user) throws NoSuchElementException, UnauthorizedException {
        Quiz quiz = quizService.findOne(quizId);

        // 열람 가능 여부 확인
        if (!quiz.isMine(user)
                && (quiz.getChallenge().getNowWeek() <= quiz.getQuizWeek()
                || !readableRepository.existsByWeekAndUserAndChall(quiz.getQuizWeek(), user, quiz.getChallenge())))
            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");

        Solve solve = findSolveOrElseEmpty(quiz, user);

        // 응답 객체 생성
        return QuizContentResponse.of(quiz, user, solve);
    }

    public QuizRublicResponse readQuizRubric(Long quizId, User user) throws UnauthorizedException {
        Solve solve = findSolveAnswer(quizId, user);

        // 응답 객체 생성
        return QuizRublicResponse.builder()
                .quizId(solve.getQuiz().getId())
                .quizRubric(solve.getQuiz().getQuizRubric())
                .build();
    }

    public QuizResponse readMyQuiz(Long quizId, User user) throws UnauthorizedException {
        Quiz quiz = quizService.findOneMine(quizId, user);
        return QuizResponse.of(quiz);
    }

    public List<QuizSummaryResponse> readMyQuizzes(Integer week, User user, Long challengeId) {
        // 퀴즈 조회
        List<Quiz> quizzes;
        if(week == 0) quizzes = quizRepository.findByUserAndChallenge_IdOrderByQuizWeekAsc(user, challengeId);
        else quizzes = quizRepository.findByQuizWeekAndUserAndChallenge_Id(week, user, challengeId);

        // 응답 개체 생성
        List<QuizSummaryResponse> responses = new ArrayList<>(quizzes.size());
        for (Quiz quiz : quizzes) {
            responses.add(QuizSummaryResponse.of(quiz, user, findSolveOrElseEmpty(quiz, user)));
        }
        return responses;
    }

    public QuizCountResponse countMyQuizzes(Integer week, User user, Long challengeId) {
        int count;
        if(week == 0) count = quizRepository.countByUserAndChallenge_Id(user, challengeId);           // 제출한 모든 퀴즈 개수
        else count = quizRepository.countByQuizWeekAndUserAndChallenge_Id(week, user, challengeId);   // 주차별 제출한 퀴즈 개수

        return QuizCountResponse.builder()
                .count(count)   // 퀴즈 개수 조회
                .challengeId(challengeId)
                .week(week == 0 ? null : week)
                .build();
    }

    private Solve findSolveOrElseEmpty(Quiz quiz, User user) {
        return solveRepository.findByQuizAndUser(quiz, user).orElseGet(Solve::empty);
    }
    public Solve findSolveAnswer(Long quizId, User user) {
        return solveRepository.findByQuiz_IdAndUserAndSolveAnswerNotNull(quizId, user).orElseThrow(() -> new UnauthorizedException("아직 풀지 않았습니다."));
    }

    private Solve findSolveAnswerAndScore(Long quizId, User user) {
        return solveRepository.findByQuiz_IdAndUserAndSolveAnswerNotNullAndSolveScoreNotNull(quizId, user).orElseThrow(() -> new UnauthorizedException("아직 채점하지 않았습니다."));
    }
}
