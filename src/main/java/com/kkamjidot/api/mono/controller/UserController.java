package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.request.LoginRequest;
import com.kkamjidot.api.mono.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "회원", description = "회원 관련 작업들")
@RestController
public class UserController {
    @Operation(summary = "개발 중)로그인 API", description = "회원 여부를 확인한다.")
    @PostMapping("v1/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return null;
    }
}
