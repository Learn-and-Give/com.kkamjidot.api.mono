package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SolveService {
    private final SolveRepository solveRepository;

    public Solve findOne(Quiz quiz, User user) throws NoSuchElementException {
        return solveRepository.findByQuizAndUser(quiz, user).orElse(Solve.empty());
    }

    public void checkSubmitAnswer(Quiz quiz, User user) {
        if (!solveRepository.existsByQuizAndUserAndSolveAnswerNotNull(quiz, user)) throw new UnauthorizedException("열람 가능한 권한이 없습니다.");
    }
}
