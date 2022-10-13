package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.request.LoginRequest;
import com.kkamjidot.api.mono.dto.request.LoginRequestV2;
import com.kkamjidot.api.mono.dto.response.LoginResponse;
import com.kkamjidot.api.mono.dto.response.LoginResponseV2;
import com.kkamjidot.api.mono.service.AuthService;
import com.kkamjidot.api.mono.service.NotificationService;
import com.kkamjidot.api.mono.service.UserService;
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
    private final UserService userService;
    private final NotificationService notificationService;

    private final AuthService authService;

    @Operation(summary = "로그인 API", description = "회원 여부를 확인한다.")
    @PostMapping("v1/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        Long userId = userService.login(request.getName(), request.getCode());  // 로그인

        if (request.getToken() != null && !request.getToken().isBlank())
            notificationService.register(userId, request.getToken(), request.getPlatform());  // 푸시 알림 용 토큰 등록

        LoginResponse response = LoginResponse.builder().userId(userId).build();  // 응답 객체 생성
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인 API V2", description = "회원 여부를 확인한다.")
    @PostMapping("v2/user/login")
    public ResponseEntity<LoginResponseV2> loginV2(@RequestBody @Valid LoginRequestV2 request) {
        String jwt = authService.login(request.getEmail(), request.getPassword(), request.getIsAutoLogin());

        LoginResponseV2 response = LoginResponseV2.builder().token(jwt).build();
        return ResponseEntity.ok(response);
    }
}