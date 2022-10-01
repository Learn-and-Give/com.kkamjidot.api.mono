package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.NotiToken;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.repository.NotiTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void register(final Long userId, final String fcmToken, boolean isAlleowdNotification) {
        User user = userService.findById(userId);
        if (validateDuplicatedToken(user, fcmToken)) {
            NotiToken notiToken = NotiToken.builder()
                    .tokenValue(fcmToken)
                    .isAllowedNoti(isAlleowdNotification)
                    .tokenDesc("fcm")
                    .tokenUser(user)
                    .build();
            notiTokenRepository.save(notiToken);
        }
    }

    public boolean validateDuplicatedToken(User user, final String fcmToken) throws IllegalStateException {
        return user.getNotiTokens().stream()
                .anyMatch(notiToken -> notiToken.getTokenValue().equals(fcmToken));
    }

    public void sendNotification(final NotificationRequest request) {
        try {
            fcmService.send(request);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
    }
}
