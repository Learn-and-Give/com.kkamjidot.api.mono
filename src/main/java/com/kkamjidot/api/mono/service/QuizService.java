package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizRepository;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final ReadableRepository readableRepository;

    public Quiz findOne(Long quizId, User user) throws NoSuchElementException, UnauthorizedException {
        Quiz quiz = quizRepository.findByIdAndQuizDeletedDateNull(quizId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 퀴즈입니다."));
        return quiz;
    }

    public void authorization(Quiz quiz, User user) throws UnauthorizedException {
        // 내가 작성한 퀴즈 여부 확인
        if (quiz.isMine(user)) return;

        // 열람 가능 여부 확인
        if (quiz.getChallenge().getNowWeek() > quiz.getQuizWeek() && readableRepository.existsByWeekAndUserAndChall(quiz.getQuizWeek(), user, quiz.getChallenge())) return;

        throw new UnauthorizedException("열람 가능한 권한이 없습니다.");
    }

    public Quiz findOneMine(Long quizId, User user) throws NoSuchElementException {
        return quizRepository.findByIdAndUserAndQuizDeletedDateNull(quizId, user).orElseThrow(() -> new UnauthorizedException("내 퀴즈가 아닙니다."));
    }

    @Transactional
    public Quiz createOne(CreateQuizRequest createQuizRequest, User user, Challenge challenge) {
        Quiz quiz = Quiz.of(createQuizRequest, user, challenge);
        return quizRepository.save(quiz);
    }

    @Transactional
    public Quiz updateOne(Long QuizId, User user, UpdateQuizRequest request) {
        Quiz quiz = findOneMine(QuizId, user);
        quiz.update(request);
        return quiz;
    }

    public Integer countAllMine(Integer week, User user, Long challengeId) {
        if(week == 0) return quizRepository.countByUserAndChallenge_Id(user, challengeId);           // 제출한 모든 퀴즈 개수
        else return quizRepository.countByQuizWeekAndUserAndChallenge_Id(week, user, challengeId);   // 주차별 제출한 퀴즈 개수
    }
}
