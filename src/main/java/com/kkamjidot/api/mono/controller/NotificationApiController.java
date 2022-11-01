package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.dto.request.RegisterFcmTokenRequest;
import com.kkamjidot.api.mono.service.AuthService;
import com.kkamjidot.api.mono.service.NotificationService;
import com.kkamjidot.api.mono.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "알림", description = "알림 관련 작업들")
@RequiredArgsConstructor
@RestController
public class NotificationApiController {
    private final NotificationService notificationService;

//    @Operation(summary = "푸시 알림용 FCM 토큰 등록 API", description = "푸시 알림용 FCM 토큰 등록한다.")
//    @ApiResponse(responseCode = "201", description = "알림 등록 성공")
//    @PostMapping("v1/notification/register")
//    public ResponseEntity<?> register(HttpServletRequest request,
//                                      @RequestBody @Valid RegisterFcmTokenRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//
//        notificationService.register(user.getId(), request.getFcmToken(), request.getPlatform());  // 푸시 알림 용 토큰 등록
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @Operation(summary = "테스트용 알림 발송 API", description = "테스트용 알림을 보낸다.")
    @ApiResponse(responseCode = "201", description = "메시지 전송 성공")
    @PostMapping("v1/notification/send")
    public ResponseEntity<?> sendTestMessage(@Parameter(description = "fcm 토큰") @RequestBody String fcmToken) {
        System.out.println(fcmToken);
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .token(fcmToken)
                .title("테스트 알림")
                .message("테스트 알림입니다.")
                .build();

        notificationService.sendNotification(notificationRequest);

        return ResponseEntity.created(null).build();
    }
}
