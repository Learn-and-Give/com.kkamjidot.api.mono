package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.request.UpdatePasswordRequest;
import com.kkamjidot.api.mono.dto.response.UserIdResponse;
import com.kkamjidot.api.mono.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "회원", description = "회원 관련 작업들")
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 비밀번호 변경 API", description = "비밀번호를 변경한다.")
    @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    @PatchMapping(path = "password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
        userService.updatePassword(request);

        return ResponseEntity.ok().build();
    }
}
