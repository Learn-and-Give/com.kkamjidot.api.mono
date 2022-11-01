package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.ScoreRequestV1;
import com.kkamjidot.api.mono.dto.request.ScoreRequestV2;
import com.kkamjidot.api.mono.dto.request.SolveRequest;
import com.kkamjidot.api.mono.dto.response.QuizIdResponse;
import com.kkamjidot.api.mono.dto.response.QuizSolveAnswerResponse;
import com.kkamjidot.api.mono.service.AuthService;
import com.kkamjidot.api.mono.service.QuizService;
import com.kkamjidot.api.mono.service.SolveService;
import com.kkamjidot.api.mono.service.TakeAClassService;
import com.kkamjidot.api.mono.service.query.QuizQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Tag(name = "퀴즈 풀기", description = "퀴즈를 풀기 위한 작업들")
@RequiredArgsConstructor
@RestController
public class QuizSolveController {
    private final QuizService quizService;
    private final TakeAClassService takeAClassService;
    private final SolveService solveService;

    @Operation(summary = "퀴즈 풀기 정답 제출 API", description = "퀴즈를 푼다. 내가 수강한 챌린지가 아니거나 이미 푼 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizIdResponse> solveQuiz(HttpServletRequest request,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid SolveRequest solveRequest,
                                                    UriComponentsBuilder uriBuilder) {
        Long userId = (Long) request.getAttribute("userId");

        Quiz quiz = quizService.findById(quizId);
        takeAClassService.checkCanReadChallengeByChallengeId(quiz.getChallengeId(), userId);
        solveService.checkNotSolved(quizId, userId);                    // 이미 푼 문제인지 확인

        solveService.solveQuiz(solveRequest.getAnswer(), quizId, userId);                              // 정답 제출

        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quiz.getId()).toUri();
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }

    @Operation(summary = "퀴즈 풀기 채점 점수 제출 API V2", description = "퀴즈를 채점한다. 퀴즈 정답을 제출한 문제가 아니거나, 이미 채점한 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v2/quizzes/{quizId}/grade")
    public ResponseEntity<QuizIdResponse> gradeQuiz(HttpServletRequest request,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid ScoreRequestV2 scoreRequestV2,
                                                    UriComponentsBuilder uriBuilder) {
        Long userId = (Long) request.getAttribute("userId");
        solveService.updateSolveScore(quizId, userId, scoreRequestV2.getScore(), scoreRequestV2.getSolveRubric());
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quizId).toUri();

        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }
}
