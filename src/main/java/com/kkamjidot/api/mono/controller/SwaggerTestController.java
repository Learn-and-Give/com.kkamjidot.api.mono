package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "데모", description = "swagger 데모 api 입니다.")
@Hidden
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {
    @Deprecated
    @Operation(summary = "demo 조회", description = "demo 조회 메서드입니다.")
    @ApiResponse(responseCode = "201", description = "조회 성공")
    @GetMapping("/test")
    public ResponseEntity<User> getTest(String memberName) {
        return ResponseEntity.created(null).body(null);
    }
}
