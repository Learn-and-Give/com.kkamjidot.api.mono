package com.kkamjidot.api.mono.commons.scheduler;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.CompleteRepository;
import com.kkamjidot.api.mono.repository.NotiTokenRepository;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
import com.kkamjidot.api.mono.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class NotificationScheduler {
    private final ChallengeRepository challengeRepository;
    private final CompleteRepository completeRepository;
    private final NotificationService notificationService;
    private final NotiTokenRepository notiTokenRepository;

//    public NotificationScheduler(ChallengeRepository challengeRepository, CompleteRepository completeRepository, NotificationService notificationService) {
//        this.challengeRepository = challengeRepository;
//        this.completeRepository = completeRepository;
//        this.notificationService = notificationService;
//    }


//    @Scheduled(cron = "0/3 * * * * ?")      // 테스트용
    @Scheduled(cron = "0 0 19 ? * FRI,SAT")
    @Scheduled(cron = "0 0 15 ? * SUN")
    public void SendDeadlineNotification() {
        // 알림 전송할 유저
        Set<User> sendUserSet = new LinkedHashSet<>();

        // 진행 중인 챌린지 리스트
        List<Challenge> runningChallengeList = challengeRepository.findAllByRunning(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        // 챌린지 별 이번 주 아직 미션을 완료하지 않은 인원들 조회
        runningChallengeList.forEach(challenge -> {
            Integer thisWeek = challenge.getThisWeek();
            List<User> userList = challenge.getTakeAClasses().stream()
                    .filter(takeAClass -> takeAClass.getTcApplicationstatus().equals(ApplicationStatus.ACCEPTED))
                    .map(TakeAClass::getUser).toList();
            userList.forEach(user -> {
                if (!completeRepository.existsByWeekAndUserAndChall(thisWeek, user, challenge)) {
                    sendUserSet.add(user);
                }
            });
        });

        String[] notificationContent = new String[]{"이번 주 배운 내용을 깜지에 기록해보세요!", "우리 깜지에서 같이 공부해봐요🤗"};

        // 알림 메시지 발송
        sendUserSet.forEach(user -> user.getNotiTokens().forEach(token -> {
            if (token.getTokenDeletedDate() == null) {
                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .token(token.getTokenValue())
                        .title("깜지")
                        .message(notificationContent[new Random().nextInt(notificationContent.length)])
                        .build();
                notificationService.sendNotification(notificationRequest);
            }
        }));
    }


//    @Scheduled(cron = "0/3 * * * * ?")      // 테스트용
    @Scheduled(cron = "0 0 9-18 ? * ?")
    public void SendCustomNotification() {
        for (String s : notiTokenRepository.findById(2L).get().getTokenValue().split("\n")) {
            System.out.println("<<>>" + s);
        }
    }
}
