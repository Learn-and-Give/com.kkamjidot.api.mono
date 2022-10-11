package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Rate;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.QuizRateRequest;
import com.kkamjidot.api.mono.dto.response.QuizRateResponse;
import com.kkamjidot.api.mono.dto.response.QuizSummaryResponse;
import com.kkamjidot.api.mono.service.*;
import com.kkamjidot.api.mono.service.query.RateQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Tag(name = "평가", description = "퀴즈의 평가(좋아요/싫어요) 관련 작업들")
@RequiredArgsConstructor
@RestController
public class RateController {
    private final UserService userService;
    private final QuizService quizService;
    private final RateService rateService;
    private final RateQueryService rateQueryService;
    private final TakeAClassService takeAClassService;

    @Operation(summary = "퀴즈 평가 API", description = "퀴즈에 좋아요(GOOD)/싫어요(BAD)/취소(null)로 평가한다. 열람 가능 주차의 문제가 아니면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 평가 성공")
    @PutMapping(path = "v1/quizzes/{quizId}/rate")
    public ResponseEntity<QuizRateResponse> rateQuiz(@RequestHeader @Parameter(description = "로그인한 회원 코드", example = "1234") String code,
                                                     @PathVariable @Parameter(description = "퀴즈 ID", example = "0") Long quizId,
                                                     @RequestBody QuizRateRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        User user = userService.authenticate_deprecated(code);
        Quiz quiz = quizService.findOneInReadableWeek(quizId, user);

        Rate rate = Rate.builder()
                .rate(request.getRate())
                .user(user)
                .quiz(quiz)
                .build();
        rateService.rateQuiz(rate);

        QuizRateResponse response = QuizRateResponse.builder()
                .cntOfGood(rateService.countOfGood(quiz))
                .quizId(rate.getQuiz().getId())
                .build();
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(response.getQuizId()).toUri();

        log.info("퀴즈 평가 API: PUT v1/quizzes/{}/rate [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "좋아요한 문제들 조회 API", description = "내가 좋아요한 문제들의 개요 리스트를 반환한다. 수강중이거나 수강했던 챌린지가 아니라면 403 에러를 반환한다.")
    @GetMapping(path = "v1/challenges/{challengeId}/my-good-quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readMyGoodQuizzes(@RequestHeader @Parameter(description = "로그인한 회원 코드", example = "1234") String code,
                                                                       @PathVariable @Parameter(description = "챌린지 ID", example = "0") Long challengeId) {
        User user = userService.authenticate_deprecated(code);
        Challenge challenge = takeAClassService.findOneChallengeTaken(challengeId, user);
        List<QuizSummaryResponse> responses = rateQueryService.readMyGoodQuizzes(user, challenge);

        log.info("좋아요한 문제들 조회 API: GET v1/challenges/{}/my-good-quizzes [User: {}, responses: {}]", challengeId, user.getId(), responses);
        return ResponseEntity.ok(responses);
    }
}
