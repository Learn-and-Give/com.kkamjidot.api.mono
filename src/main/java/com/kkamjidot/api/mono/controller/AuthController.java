package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.request.LoginRequest;
import com.kkamjidot.api.mono.dto.response.LoginResponse;
import com.kkamjidot.api.mono.service.AuthService;
import com.kkamjidot.api.mono.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "인증", description = "인증 관련 작업들")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final NotificationService notificationService;

    @Operation(summary = "로그인 API V2", description = "회원 여부를 확인한다.")
    @PostMapping("v2/user/login")
    public ResponseEntity<LoginResponse> loginV2(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request.getEmail(), request.getPassword());

        if (request.getFcmToken() != null && !request.getFcmToken().isBlank())
            notificationService.register(response.getUserId(), request.getFcmToken(), request.getPlatform());  // 푸시 알림 용 토큰 등록

        return ResponseEntity.ok(response);
    }
}
