package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.response.PointResponse;
import com.kkamjidot.api.mono.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "포인트", description = "포인트 관련 작업들")
@RequiredArgsConstructor
@RestController
public class PointController {
    private final PointService pointService;

    @Operation(summary = "포인트 조회 API", description = "현재 보유 포인트를 조회한다.")
    @GetMapping("v1/my/point")
    public ResponseEntity<PointResponse> readQuiz(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        PointResponse response = PointResponse.builder()
                .userId(userId)
                .point(pointService.findByUser(userId))
                .build();

        return ResponseEntity.ok(response);
    }
}