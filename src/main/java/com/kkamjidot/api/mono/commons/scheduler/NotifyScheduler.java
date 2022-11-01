package com.kkamjidot.api.mono.commons.scheduler;

import com.kkamjidot.api.mono.domain.*;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.CompleteRepository;
import com.kkamjidot.api.mono.repository.NotificationSchedulerRepository;
import com.kkamjidot.api.mono.repository.query.ChallengeQueryRepository;
import com.kkamjidot.api.mono.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
public class NotifyScheduler {
    private final ChallengeRepository challengeRepository;
    private final ChallengeQueryRepository challengeQueryRepository;
    private final CompleteRepository completeRepository;
    private final NotificationSchedulerRepository notificationSchedulerRepository;
    private final NotificationService notificationService;


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
                if (!completeRepository.existsByWeekAndUserIdAndChallId(thisWeek, user.getId(), challenge.getId())) {
                    sendUserSet.add(user);
                }
            });
        });

        String[] notificationContents = new String[]{"이번 주 배운 내용을 깜지에 기록해보세요!", "우리 깜지에서 같이 공부해봐요🤗"};

        // 알림 메시지 발송
        sendUserSet.forEach(user -> sendNotificationToUser("깜지", notificationContents, user));
    }

//    @Scheduled(cron = "0/3 * 5 * * ?")      // 테스트용
    @Scheduled(cron = "0 0 9-18 ? * ?")
    public void SendCustomNotification() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<NotificationScheduler> notificationSchedulerList = notificationSchedulerRepository.findByNsDayAndNsHour(now.getDayOfWeek().getValue(), now.getHour());

        notificationSchedulerList.forEach(notificationScheduler -> {
            NotificationContent notificationContent = notificationScheduler.getNotificationContent();
            String[] notificationContents = notificationContent.getNcContent().split("\n");
            notificationScheduler.getNotifies().forEach(notify -> sendNotificationToUser(notificationContent.getNcTitle(), notificationContents, notify.getUser()));
        });
    }

    private void sendNotificationToUser(String notificationTitle, String[] notificationContents, User user) {
        user.getNotificationTokens().forEach(token -> {
            if (token.getTokenDeletedDate() == null) {
                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .token(token.getTokenValue())
                        .title(notificationTitle)
                        .message(notificationContents[new Random().nextInt(notificationContents.length)])
                        .build();
                notificationService.sendNotification(notificationRequest);
            }
        });
    }
}
