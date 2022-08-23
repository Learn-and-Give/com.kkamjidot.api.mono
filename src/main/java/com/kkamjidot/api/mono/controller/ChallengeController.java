package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.response.ChallengeResponse;
import com.kkamjidot.api.mono.dto.response.ThisWeekResponse;
import com.kkamjidot.api.mono.dto.response.WeekResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "챌린지", description = "챌린지 관련 작업들")
@RestController
public class ChallengeController {
    @Operation(summary = "개발 중)챌린지 목록 조회 API", description = "모든 챌린지를 조회한다.")
    @GetMapping("v1/challenges")
    public ResponseEntity<List<ChallengeResponse>> readChallenges(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code) {
        return null;
    }

    @Operation(summary = "개발 중)내가 참여한 챌린지 목록 조회 API", description = "내가 참여한 챌린지 목록을 조회한다.")
    @GetMapping("v1/my/challenges")
    public ResponseEntity<List<ChallengeResponse>> readMyChallenges(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code) {
        return null;
    }

    @Operation(summary = "개발 중)챌린지 주차 정보 목록 조회 API", description = "한 챌린지의 주차별 열람가능 여부 정보 목록을 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/weeks")
    public ResponseEntity<List<WeekResponse>> readWeeks(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                        @PathVariable Long challengeId) {
        return null;
    }

    @Operation(summary = "개발 중)현재 주자 반환 API", description = "현재 일시와 현재 챌린지에서의 주차를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/now")
    public ResponseEntity<ThisWeekResponse> now(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                @PathVariable Long challengeId) {
        return null;
    }
}
