package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TakeAClassService {
    private final TakeAClassRepository takeAClassRepository;
    private final QuizService quizService;

    /**
     * 수강중이고 진행중인 챌린지를 조회한다.
     */
    public void checkUserTakesChallengeAndInProgress(Long challengeId, Long userId) throws UnauthorizedException {
        Challenge challenge = takeAClassRepository.findByTcApplicationstatusAndChallIdAndUserId(ApplicationStatus.ACCEPTED, challengeId, userId)
                .orElseThrow(() -> new UnauthorizedException("수강중인 챌린지가 아닙니다.")).getChall();
        if (!challenge.isInProgress()) throw new UnauthorizedException("이미 종료된 챌린지입니다.");
    }

    /**
     * 열람 가능한 챌린지인지 검사한다.
     */
    public void checkCanReadChallengeByChallengeId(Long challengeId, Long userId) {
        if (Boolean.FALSE.equals(takeAClassRepository.existsByChall_IdAndUser_IdAndTcApplicationstatus(challengeId, userId, ApplicationStatus.ACCEPTED)))
            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");
    }

    /**
     * 열람 가능한 퀴즈인지 검사한다.
     */
    public void checkCanReadChallengeByQuizId(Long quizId, Long userId) {
        Long challengeId = quizService.findById(quizId).getChallengeId();

        if (Boolean.FALSE.equals(takeAClassRepository.existsByChall_IdAndUser_IdAndTcApplicationstatus(challengeId, userId, ApplicationStatus.ACCEPTED)))
            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");
    }
}
