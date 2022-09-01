package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.controller.ChallengeController;
import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Readable;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReadableService {
    private final Logger LOGGER = LoggerFactory.getLogger(ChallengeController.class);

    private final ReadableRepository readableRepository;
    private final QuizService quizService;


    /**
     * 퀴즈를 열람 가능한지 권한을 확인한다. 특히, 퀴즈의 주차가 현재 주차보다 이전이고, 열람 가능한 권한이 있는지 검사한다.
     * */
    public Quiz findOneInReadableWeek(Long quizId, User user) throws UnauthorizedException {
        Quiz quiz = quizService.findOne(quizId);

        // 퀴즈의 주차가 현재 주차보다 이전이고, 열람 가능한 권한이 있는지 검사
        if (quiz.getChallenge().getNowWeek() <= quiz.getQuizWeek() || !readableRepository.existsByWeekAndUserAndChall(quiz.getQuizWeek(), user, quiz.getChallenge()))
            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");

        return quiz;
    }

    /**
     * 챌린지 퀴즈 제출 조건 이상의 퀴즈를 제출했으면 열람 가능한 권한을 생성한다.
     * @param challenge 챌린지
     * @param user 회원
     * @param week 권한 생성하기 위한 주차
     */
    @Transactional
    public void createOneIfRight(Challenge challenge, User user, int week) {
        int count = quizService.countByWeek(challenge, user, week);
        if(challenge.isCountOfQuizzesIsEnough(count)) return;

        if(readableRepository.existsByWeekAndUserAndChall(week, user, challenge)) return;

        Readable readable = Readable.builder()
                .week(week)
                .user(user)
                .chall(challenge)
                .build();
        readableRepository.save(readable);
        LOGGER.info("Readable created: {}", readable);
    }
}
