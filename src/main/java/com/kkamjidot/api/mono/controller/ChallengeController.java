package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.response.ChallengeResponse;
import com.kkamjidot.api.mono.dto.response.ChallengeSummaryResponse;
import com.kkamjidot.api.mono.dto.response.nowResponse;
import com.kkamjidot.api.mono.dto.response.WeekResponse;
import com.kkamjidot.api.mono.service.AuthService;
import com.kkamjidot.api.mono.service.UserService;
import com.kkamjidot.api.mono.service.query.ChallengeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "챌린지", description = "챌린지 관련 작업들")
@RequiredArgsConstructor
@RestController
public class ChallengeController {
    private final ChallengeQueryService challengeQueryService;

    @Operation(summary = "챌린지 목록 조회 API", description = "모든 챌린지를 조회한다.")
    @GetMapping("v1/challenges")
    public ResponseEntity<List<ChallengeSummaryResponse>> readChallenges(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        List<ChallengeSummaryResponse> responses = challengeQueryService.readChallenges(userId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "챌린지 조회 API", description = "한 챌린지 정보를 조회한다.")
    @GetMapping("v1/challenges/{challengeId}")
    public ResponseEntity<ChallengeResponse> readChallenge(HttpServletRequest request,
                                                           @PathVariable Long challengeId) {
        Long userId = (Long) request.getAttribute("userId");

        ChallengeResponse response = challengeQueryService.readChallenge(challengeId, userId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 참여한 챌린지 목록 조회 API", description = "내가 참여한 챌린지 목록을 조회한다.")
    @GetMapping("v1/my/challenges")
    public ResponseEntity<List<ChallengeSummaryResponse>> readMyChallenges(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        List<ChallengeSummaryResponse> responses = challengeQueryService.readMyChallenges(userId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "챌린지 주차 정보 목록 조회 API", description = "한 챌린지의 주차별 열람가능 여부 정보 목록을 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/weeks")
    public ResponseEntity<WeekResponse> readWeeks(HttpServletRequest request,
                                                  @PathVariable Long challengeId) {
        Long userId = (Long) request.getAttribute("userId");

        WeekResponse response = challengeQueryService.readWeeks(challengeId, userId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "현재 주자 반환 API", description = "현재 일시와 현재 챌린지에서의 주차를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/now")
    public ResponseEntity<nowResponse> readThisWeek(HttpServletRequest request,
                                                    @PathVariable Long challengeId) {
        Long userId = (Long) request.getAttribute("userId");

        nowResponse response = challengeQueryService.readThisWeek(challengeId);

        return ResponseEntity.ok(response);
    }
}
