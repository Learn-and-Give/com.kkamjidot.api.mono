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

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "퀴즈 풀기", description = "퀴즈를 풀기 위한 작업들")
@RequiredArgsConstructor
@RestController
public class QuizSolveController {
    private final QuizService quizService;
    private final QuizQueryService quizQueryService;
    private final TakeAClassService takeAClassService;
    private final SolveService solveService;
    private final AuthService authService;

    @Operation(summary = "퀴즈 풀기 정답 제출 API", description = "퀴즈를 푼다. 내가 수강한 챌린지가 아니거나 이미 푼 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizIdResponse> solveQuiz(@RequestHeader String jwt,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid SolveRequest request,
                                                    UriComponentsBuilder uriBuilder) {
        User user = authService.authenticate(jwt);

        Quiz quiz = quizService.findById(quizId);
        takeAClassService.checkCanReadChallengeByChallengeId(quiz.getChallengeId(), user.getId());
        solveService.checkNotSolved(quiz, user);                    // 이미 푼 문제인지 확인

        Solve solve = Solve.of(request, quiz, user);
        solveService.createOne(solve);                              // 정답 제출
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quiz.getId()).toUri();

        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }

    @Operation(summary = "퀴즈 풀었던 정답 조회 API", description = "한 문제에 내가 제출한 정답을 조회한다.")
    @GetMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizSolveAnswerResponse> readQuizSolvedAnswer(@RequestHeader String jwt,
                                                                        @PathVariable Long quizId) {
        User user = authService.authenticate(jwt);                     // 회원 인증
        Solve solve = quizQueryService.findSolve(quizId, user);   // 제출한 정답 조회

        // 응답 객체 생성
        QuizSolveAnswerResponse response = QuizSolveAnswerResponse.builder()
                .quizId(solve.getQuiz().getId())
                .solveAnswer(solve.getSolveAnswer())
                .solveRubric(solve.getSolveRubric())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 풀기 채점 점수 제출 API V1", description = "퀴즈를 채점한다. 퀴즈 정답을 제출한 문제가 아니거나, 이미 채점한 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/grade")
    public ResponseEntity<QuizIdResponse> gradeQuiz(@RequestHeader String jwt,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid ScoreRequestV1 request,
                                                    UriComponentsBuilder uriBuilder) {
        User user = authService.authenticate(jwt);
        solveService.updateSolveScore(quizId, user.getId(), request.getScore(), null);
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quizId).toUri();

        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }

    @Operation(summary = "퀴즈 풀기 채점 점수 제출 API V2", description = "퀴즈를 채점한다. 퀴즈 정답을 제출한 문제가 아니거나, 이미 채점한 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v2/quizzes/{quizId}/grade")
    public ResponseEntity<QuizIdResponse> gradeQuiz(@RequestHeader String jwt,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid ScoreRequestV2 request,
                                                    UriComponentsBuilder uriBuilder) {
        User user = authService.authenticate(jwt);
        solveService.updateSolveScore(quizId, user.getId(), request.getScore(), request.getChosenRubric());
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quizId).toUri();

        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }
}
