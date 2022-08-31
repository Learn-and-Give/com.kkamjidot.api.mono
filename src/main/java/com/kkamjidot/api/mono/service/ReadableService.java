package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Readable;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReadableService {
    private final ReadableRepository readableRepository;

    private final QuizService quizService;

    public Quiz authenticate(Long quizId, User user) throws UnauthorizedException {
        Quiz quiz = quizService.findOne(quizId);

        if (quiz.getChallenge().getNowWeek() <= quiz.getQuizWeek() || !readableRepository.existsByWeekAndUserAndChall(quiz.getQuizWeek(), user, quiz.getChallenge()))
            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");

        return quiz;
    }
}
