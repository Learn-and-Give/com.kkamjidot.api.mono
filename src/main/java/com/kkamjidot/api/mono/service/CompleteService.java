package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.controller.ChallengeController;
import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Complete;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.repository.CompleteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompleteService {
    private final Logger LOGGER = LoggerFactory.getLogger(ChallengeController.class);

    private final CompleteRepository completeRepository;
    private final QuizService quizService;

    /**
     * 챌린지 퀴즈 제출 조건 이상의 퀴즈를 제출했으면 열람 가능한 권한을 생성한다.
     * @param challenge 챌린지
     * @param user 회원
     * @param week 권한 생성하기 위한 주차
     */
    @Transactional
    public void createOneIfRight(Challenge challenge, User user, int week) {
        int count = quizService.countByWeek(challenge, user, week);
        if (challenge.isCountOfQuizzesIsEnough(count)) return;

        if (completeRepository.existsByWeekAndUserAndChall(week, user, challenge)) return;

        Complete complete = Complete.builder()
                .week(week)
                .user(user)
                .chall(challenge)
                .build();
        completeRepository.save(complete);
        LOGGER.info("Readable created: {}", complete);
    }

    public List<Integer> findCompleteWeeks(User user, Challenge challenge) {
        return completeRepository.findByUserAndChall(user, challenge).stream().map(Complete::getWeek).toList();  // 미션 완료 주차 조회
    }

    public int countComplete(Challenge challenge) {
        return completeRepository.countByChallAndWeek(challenge, challenge.getThisWeek());
    }
}
