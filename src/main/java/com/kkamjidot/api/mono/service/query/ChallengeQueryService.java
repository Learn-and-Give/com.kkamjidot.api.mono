package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.dto.response.ChallengeResponse;
import com.kkamjidot.api.mono.dto.response.ChallengeSummaryResponse;
import com.kkamjidot.api.mono.dto.response.WeekResponse;
import com.kkamjidot.api.mono.dto.response.enumerate.WeekStatus;
import com.kkamjidot.api.mono.dto.response.nowResponse;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
import com.kkamjidot.api.mono.service.ChallengeService;
import com.kkamjidot.api.mono.service.CompleteService;
import com.kkamjidot.api.mono.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChallengeQueryService {
    private final ChallengeRepository challengeRepository;
    private final TakeAClassRepository takeAClassRepository;
    private final CompleteService completeService;
    private final ChallengeService challengeService;
    private final UserService userService;

    public List<ChallengeSummaryResponse> readChallenges(Long userId) {
        User user = userService.findById(userId);

        // 챌린지 목록 조회
        List<Challenge> challenges = challengeRepository.findByChallDeletedDateNull();

        // 응답 객체 생성
        List<ChallengeSummaryResponse> challengeSummaryResponses = new ArrayList<>(challenges.size());
        for (Challenge challenge : challenges) {
            ChallengeSummaryResponse challengeSummaryResponse = ChallengeSummaryResponse.of(challenge);
            findApplicationStatus(challenge, user).ifPresent(challengeSummaryResponse::setApplicationStatus);     // 신청상태
            challengeSummaryResponses.add(challengeSummaryResponse);
        }

        return challengeSummaryResponses;
    }

    public ChallengeResponse readChallenge(Long challengeId, Long userId) {
        User user = userService.findById(userId);

        // 챌린지 조회
        Challenge challenge = challengeService.findById(challengeId);
        ChallengeResponse challengeResponse = ChallengeResponse.of(challenge, completeService.countComplete(challenge));
        findApplicationStatus(challenge, user).ifPresent(challengeResponse::setApplicationStatus);     // 신청상태
        return challengeResponse;
    }

    private Optional<ApplicationStatus> findApplicationStatus(Challenge challenge, User user) throws NoSuchElementException {
        return takeAClassRepository.findByChallAndUser(challenge, user).map(TakeAClass::getTcApplicationstatus);
    }

    public List<ChallengeSummaryResponse> readMyChallenges(Long userId) {
        // 수강 목록 조회
        List<TakeAClass> takes = takeAClassRepository.findByUserId(userId);

        // 응답 객체 생성
        List<ChallengeSummaryResponse> challengeSummaryResponses = new ArrayList<>(takes.size());
        for (TakeAClass take : takes) {
            ChallengeSummaryResponse challengeSummaryResponse = ChallengeSummaryResponse.of(take.getChall());
            challengeSummaryResponse.setApplicationStatus(take.getTcApplicationstatus());
            challengeSummaryResponses.add(challengeSummaryResponse);
        }

        return challengeSummaryResponses;
    }

    public WeekResponse readWeeks(Long challengeId, Long userId) {
        User user = userService.findById(userId);
        Challenge challenge = challengeService.findById(challengeId);
        List<Integer> completeWeeks = completeService.findCompleteWeeks(user, challenge);
        int challTotalWeeks = challenge.getChallTotalWeeks();   // 총 주차 조회

        // 열람 가능한 주차 true로 변경
        Map<Integer, WeekStatus> weeks = new HashMap<>(challTotalWeeks);
        for (int i = 1; i <= challTotalWeeks; ++i) {
            if (i < challenge.getThisWeek()) {
                if (completeWeeks.contains(i)) weeks.put(i, WeekStatus.READABLE);
                else weeks.put(i, WeekStatus.UNREADABLE);
            } else if (i == challenge.getThisWeek() && completeWeeks.contains(i)) weeks.put(i, WeekStatus.READABLE_CLOSED);
            else weeks.put(i, WeekStatus.CLOSED);
        }

        return WeekResponse.builder()
                .challengeId(challengeId)
                .totalWeeks(challTotalWeeks)
                .weeks(weeks)
                .build();
    }

    public nowResponse readThisWeek(Long challengeId) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Challenge challenge = challengeService.findById(challengeId);

        return nowResponse.builder()
                .week(challenge.getThisWeek())
                .challengeId(challengeId)
                .challStartDate(challenge.getChallStartDate())
                .now(now)
                .build();
    }
}
