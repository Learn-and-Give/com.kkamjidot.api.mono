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
    private final CompleteRepository completeRepository;
    private final ChallengeService challengeService;
    private final UserService userService;

    /**
     * 챌린지 퀴즈 제출 조건 이상의 퀴즈를 제출했으면 열람 가능한 권한을 생성한다.
     * @param challengeId 챌린지 ID
     * @param userId 회원 ID
     * @param week 권한 생성하기 위한 주차
     */
    @Transactional
    public void createOneIfRight(Long challengeId, Long userId, int week) {
        if (completeRepository.existsByWeekAndUserIdAndChallId(week, userId, challengeId)) return;
        Challenge challenge = challengeService.findById(challengeId);

        User user = userService.findById(userId);
        Complete complete = Complete.builder()
                .week(week)
                .user(user)
                .chall(challenge)
                .build();
        completeRepository.save(complete);
    }

    public List<Integer> findCompleteWeeks(User user, Challenge challenge) {
        return completeRepository.findByUserAndChall(user, challenge).stream().map(Complete::getWeek).toList();  // 미션 완료 주차 조회
    }

    public int countComplete(Challenge challenge) {
        return completeRepository.countByChallAndWeek(challenge, challenge.getThisWeek());
    }
}
