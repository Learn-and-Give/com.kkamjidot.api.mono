package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.response.ChallengeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "챌린지", description = "챌린지 관련 작업들")
@RestController
public class ChallengeController {
    @Operation(summary = "챌린지 리스트 조회 API", description = "모든 챌린지를 조회한다.")
    @GetMapping("v1/challenges")
    public ResponseEntity<List<ChallengeResponse>> readChallenges(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code) {
        return null;
    }

    @Operation(summary = "내가 참여한 챌린지 리스트 조회 API", description = "내가 참여한 챌린지 리스트를 조회한다.")
    @GetMapping("v1/my/challenges")
    public ResponseEntity<List<ChallengeResponse>> readMyChallenges(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code) {
        return null;
    }
}
