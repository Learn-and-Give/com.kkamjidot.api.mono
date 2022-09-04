package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.LoginRequest;
import com.kkamjidot.api.mono.dto.response.LoginResponse;
import com.kkamjidot.api.mono.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "회원", description = "회원 관련 작업들")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "로그인 API", description = "회원 여부를 확인한다.")
    @PostMapping("v1/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.login(request.getName(), request.getCode());  // 로그인
        LoginResponse response = LoginResponse.builder().userId(user.getId()).build();  // 응답 객체 생성
        return ResponseEntity.ok(response);
    }
}
