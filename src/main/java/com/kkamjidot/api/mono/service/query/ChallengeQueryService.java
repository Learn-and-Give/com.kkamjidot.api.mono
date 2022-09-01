package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Readable;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.dto.response.ChallengeResponse;
import com.kkamjidot.api.mono.dto.response.WeekResponse;
import com.kkamjidot.api.mono.dto.response.enumerate.WeekStatus;
import com.kkamjidot.api.mono.dto.response.nowResponse;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
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
    private final ReadableRepository readableRepository;

    public List<ChallengeResponse> readChallenges(User user) {
        // 챌린지 목록 조회
        List<Challenge> challenges = challengeRepository.findByChallDeletedDateNull();

        // 응답 객체 생성
        List<ChallengeResponse> challengeResponses = new ArrayList<>(challenges.size());
        for (Challenge challenge : challenges) {
            ChallengeResponse challengeResponse = ChallengeResponse.of(challenge);
            findApplicationStatus(challenge, user).ifPresent(challengeResponse::setApplicationStatus);     // 신청상태
            challengeResponses.add(challengeResponse);
        }

        return challengeResponses;
    }

    public ChallengeResponse readChallenge(Long challengeId, User user) {
        // 챌린지 조회
        Challenge challenge = findChallenge(challengeId);
        ChallengeResponse challengeResponse = ChallengeResponse.of(challenge);
        findApplicationStatus(challenge, user).ifPresent(challengeResponse::setApplicationStatus);     // 신청상태
        return challengeResponse;
    }

    private Optional<ApplicationStatus> findApplicationStatus(Challenge challenge, User user) throws NoSuchElementException {
        return takeAClassRepository.findByChallAndUser(challenge, user).map(TakeAClass::getTcApplicationstatus);
    }

    public List<ChallengeResponse> readMyChallenges(User user) {
        // 수강 목록 조회
        List<TakeAClass> takes = takeAClassRepository.findByUser(user);

        // 응답 객체 생성
        List<ChallengeResponse> challengeResponses = new ArrayList<>(takes.size());
        for (TakeAClass take: takes) {
            ChallengeResponse challengeResponse = ChallengeResponse.of(take.getChall());
            challengeResponse.setApplicationStatus(take.getTcApplicationstatus());
            challengeResponses.add(challengeResponse);
        }

        return challengeResponses;
    }

    public WeekResponse readWeeks(Long challengeId, User user) {
        Challenge challenge = findChallenge(challengeId);
        List<Integer> readableWeeks = readableRepository.findByUserAndChall(user, challenge).stream().map(Readable::getWeek).toList();  // 열람 가능 주차 조회
        int challTotalWeeks = challenge.getChallTotalWeeks();   // 총 주차 조회

        // 열람 가능한 주차 true로 변경
        Map<Integer, WeekStatus> weeks = new HashMap<>(challTotalWeeks);
        for(int i = 1; i <= challTotalWeeks; ++i) {
            if(i < challenge.getNowWeek()) {
                if (readableWeeks.contains(i)) weeks.put(i, WeekStatus.READABLE);
                else weeks.put(i, WeekStatus.UNREADABLE);
            } else weeks.put(i, WeekStatus.CLOSED);
        }

        return WeekResponse.builder()
                .challengeId(challengeId)
                .totalWeeks(challTotalWeeks)
                .weeks(weeks)
                .build();
    }

    public nowResponse readThisWeek(Long challengeId) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Challenge challenge = findChallenge(challengeId);

        return nowResponse.builder()
                .week(challenge.getNowWeek())
                .challengeId(challengeId)
                .challStartDate(challenge.getChallStartDate())
                .now(now)
                .build();
    }

    public Challenge findChallenge(Long challengeId) {
        return challengeRepository.findByIdAndChallDeletedDateNull(challengeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
    }
}
