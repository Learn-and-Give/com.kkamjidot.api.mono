package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.NotiToken;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.repository.NotiTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.ScatteringByteChannel;
import java.util.concurrent.ExecutionException;

@Slf4j
@Transactional(readOnly = true)
@Service
public class NotificationService {
    private final FCMService fcmService;
    private final UserService userService;
    private final NotiTokenRepository notiTokenRepository;

    public NotificationService(final FCMService fcmService, UserService userService, NotiTokenRepository notiTokenRepository) {
        this.fcmService = fcmService;
        this.userService = userService;
        this.notiTokenRepository = notiTokenRepository;
    }

    @Transactional
    public void register(final Long userId, final String fcmToken, String platform) {
        User user = userService.findById(userId);

        try {
            validateDuplicatedToken(user, fcmToken);
            NotiToken notiToken = NotiToken.builder()
                    .tokenValue(fcmToken)
                    .platform(platform)
                    .tokenDesc("fcm")
                    .tokenUser(user)
                    .build();
            notiTokenRepository.save(notiToken);
        } catch (IllegalStateException e) {
            log.info(e.getMessage());
        }
    }

    private void validateDuplicatedToken(User user, final String fcmToken) throws IllegalStateException {
        user.getNotiTokens().stream()
                .filter(notiToken -> notiToken.getTokenValue().equals(fcmToken))
                .findAny()
                .ifPresent(notiToken -> {
                    throw new IllegalStateException("이미 등록된 토큰입니다.");
                });
    }

    public void sendNotification(final NotificationRequest request) {
        try {
            fcmService.send(request);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
    }
}
