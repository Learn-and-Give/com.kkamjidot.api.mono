package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public Quiz findOne(Long quizId) throws NoSuchElementException {
        return quizRepository.findByIdAndQuizDeletedDateNull(quizId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 퀴즈입니다."));
    }

    @Transactional
    public Quiz createOne(CreateQuizRequest createQuizRequest, User user, Challenge challenge) {
        Quiz quiz = Quiz.of(createQuizRequest, user, challenge);
        return quizRepository.save(quiz);
    }
}
