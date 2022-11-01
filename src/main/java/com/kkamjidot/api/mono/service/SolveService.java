package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Point;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.PointType;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.PointRepository;
import com.kkamjidot.api.mono.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SolveService {
    private final SolveRepository solveRepository;
    private final PointRepository pointRepository;
    private final QuizService quizService;
    private final UserService userService;

    public void checkNotSolved(Long quizId, Long userId) throws UnauthorizedException {
        if (solveRepository.existsByUserIdAndQuizId(userId, quizId)) throw new UnauthorizedException("이미 풀었습니다.");
    }

    public void checkSolved(Long quizId, Long userId) throws UnauthorizedException {
        if (!solveRepository.existsByUserIdAndQuizId(userId, quizId)) throw new UnauthorizedException("아직 풀지 않았습니다.");
    }

    @Transactional
    public void solveQuiz(String answer, Long quizId, Long userId) {
        Quiz quiz = quizService.findById(quizId);
        User user = userService.findById(userId);
        Solve solve = Solve.of(answer, quiz, user);
        solveRepository.save(solve);

        Point point = Point.builder()
                .poiDesc("퀴즈 풀이")
                .poiIsIncrease(true)
                .poiType(PointType.SOLVED)
                .poiValue(100)
                .poiRelatedId("solve_" + solve.getId())
                .user(quiz.getUser())
                .preBalance(pointRepository.findFirstByUserIdOrderByPoiDatetimeDesc(quiz.getUser().getId()).getPoiBalance())
                .build();

        pointRepository.save(point);
    }

    @Transactional
    public void updateSolveScore(Long quizId, Long userId, int score, String solveRubric) {
        Solve solve = solveRepository.findByUserIdAndQuizId(userId, quizId).orElseThrow(() -> new UnauthorizedException("아직 풀지 않았습니다."));
        if (solve.getSolveScore() != null) throw new UnauthorizedException("이미 채점한 문제입니다.");
        solve.enterScore(score, solveRubric);
    }

    public Solve findSolveOrElseEmpty(Long userId, Long quizId) {
        return solveRepository.findByUserIdAndQuizId(userId, quizId).orElseGet(Solve::empty);
    }

    public Solve findSolve(Long userId, Long quizId) {
        return solveRepository.findByUserIdAndQuizId(userId, quizId).orElseThrow(() -> new UnauthorizedException("아직 풀지 않았습니다."));
    }

    public Integer numberOfQuizzesSolved(Long quizId) {
        return solveRepository.countByQuizId(quizId);
    }
}
