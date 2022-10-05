package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.NotificationRequest;
import com.kkamjidot.api.mono.service.NotificationService;
import com.kkamjidot.api.mono.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "알림", description = "알림 관련 작업들")
@RestController
@RequestMapping("v1/notification")
public class NotificationApiController {
    private final UserService userService;
    private final NotificationService notificationService;

    public NotificationApiController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

//    @Operation(summary = "푸시 알림용 FCM 토큰 등록 API", description = "푸시 알림용 FCM 토큰 등록한다.")
//    @ApiResponse(responseCode = "201", description = "알림 등록 성공")
//    @PostMapping("register")
//    public ResponseEntity<?> register(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
//                                      @Parameter(description = "fcm 토큰") @RequestBody String fcmToken) {
//        User user = userService.authenticate(code);
//
//        notificationService.register(user.getId(), fcmToken, );
//
//        return ResponseEntity.created(null).build();
//    }

    @Operation(summary = "테스트용 알림 발송 API", description = "테스트용 알림을 보낸다.")
    @ApiResponse(responseCode = "201", description = "메시지 전송 성공")
    @PostMapping("send")
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
