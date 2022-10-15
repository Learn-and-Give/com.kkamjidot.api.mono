package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SolveService {
    private final SolveRepository solveRepository;

    public void checkNotSolved(Quiz quiz, User user) throws UnauthorizedException {
        if (solveRepository.existsByQuizAndUser(quiz, user)) throw new UnauthorizedException("이미 풀었습니다.");
    }

    public void checkSolved(Long quizId, Long userId) throws UnauthorizedException {
        if (!solveRepository.existsByQuizIdAndUserId(quizId, userId)) throw new UnauthorizedException("아직 풀지 않았습니다.");
    }

    @Transactional
    public void createOne(Solve solve) {
        solveRepository.save(solve);
    }

    @Transactional
    public void updateSolveScore(Long quizId, Long userId, int score, String solveRubric) {
        Solve solve = solveRepository.findByQuizIdAndUserId(quizId, userId).orElseThrow(() -> new UnauthorizedException("아직 풀지 않았습니다."));
        if (solve.getSolveScore() != null) throw new UnauthorizedException("이미 채점한 문제입니다.");
        solve.enterScore(score, solveRubric);
    }

    public Solve findSolveOrElseEmpty(Quiz quiz, User user) {
        return solveRepository.findByQuizAndUser(quiz, user).orElseGet(Solve::empty);
    }

    public Solve findSolveOrElseEmpty(Long quizId, Long userId) {
        return solveRepository.findByQuizIdAndUserId(quizId, userId).orElseGet(Solve::empty);
    }

    public Solve findSolve(Long quizId, Long userId) {
        return solveRepository.findByQuizIdAndUserId(quizId, userId).orElseThrow(() -> new UnauthorizedException("아직 풀지 않았습니다."));
    }

    public Integer numberOfQuizzesSolved(Long quizId) {
        return solveRepository.countByQuizId(quizId);
    }
}
