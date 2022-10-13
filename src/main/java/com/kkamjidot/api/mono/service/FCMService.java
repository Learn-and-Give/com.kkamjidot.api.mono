package com.kkamjidot.api.mono.service;

import com.google.firebase.messaging.*;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FCMService {
    public void send(final NotificationRequest notificationRequest) throws InterruptedException, ExecutionException {
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setNotification(Notification.builder()
                        .setTitle(notificationRequest.getTitle())
                        .setBody(notificationRequest.getMessage())
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        log.info("Sent message: " + response);
    }
}
