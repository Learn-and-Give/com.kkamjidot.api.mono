package com.kkamjidot.api.mono.commons.scheduler;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.CompleteRepository;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
import com.kkamjidot.api.mono.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional(readOnly = true)
public class NotificationScheduler {
    private final ChallengeRepository challengeRepository;
    private final TakeAClassRepository takeAClassRepository;
    private final CompleteRepository completeRepository;
    private final NotificationService notificationService;

    public NotificationScheduler(ChallengeRepository challengeRepository, TakeAClassRepository takeAClassRepository, CompleteRepository completeRepository, NotificationService notificationService) {
        this.challengeRepository = challengeRepository;
        this.takeAClassRepository = takeAClassRepository;
        this.completeRepository = completeRepository;
        this.notificationService = notificationService;
    }


//    @Scheduled(cron = "0/10 * * * * ?")      // 테스트용 매 분 실행
    @Scheduled(cron = "0 0 19 ? * FRI,SAT")
    @Scheduled(cron = "0 0 15 ? * SUN")
    public void SendDeadlineNotification() {
        Set<User> sendUserSet = new LinkedHashSet<>();

        List<Challenge> runningChallengeList = challengeRepository.findAllByRunning(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

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

        sendUserSet.forEach(user -> user.getNotiTokens().forEach(token -> {
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .token(token.getTokenValue())
                    .title("깜지")
                    .message("이번 주 배운 내용을 깜지에 기록해보세요!")
                    .build();

            notificationService.sendNotification(notificationRequest);
        }));
    }
}
