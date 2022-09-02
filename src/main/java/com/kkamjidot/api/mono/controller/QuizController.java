package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.*;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.ScoreRequest;
import com.kkamjidot.api.mono.dto.request.SolveRequest;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.dto.response.*;
import com.kkamjidot.api.mono.service.*;
import com.kkamjidot.api.mono.service.query.QuizQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "퀴즈", description = "퀴즈 관련 작업들")
@RequiredArgsConstructor
@RestController
public class QuizController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final QuizService quizService;
    private final QuizQueryService quizQueryService;
    private final TakeAClassService takeAClassService;
    private final SolveService solveService;
    private final ReadableService readableService;

    @Operation(summary = "퀴즈 개요 목록 조회 API", description = "한 챌린지에 여러 주차에 해당하는 퀴즈의 개요 목록을 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다. 쿼리 week에는 여러 주차를 입력받는다.")
    @GetMapping("v1/challenges/{challengeId}/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readQuizSummaries(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                       @PathVariable Long challengeId,
                                                                       @RequestParam int[] week) {
        User user = userService.authenticate(code);

        List<QuizSummaryResponse> responses = quizQueryService.readQuizSummaries(user, challengeId, week);

        LOGGER.info("퀴즈 개요 목록 조회 API: Get v1/challenges/{challengeId}/quizzes?week={} [User: {}, responses: {}]", week, user.getId(), responses);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "퀴즈 문제 조회 API", description = "퀴즈의 문제 내용을 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/content")
    public ResponseEntity<QuizContentResponse> readQuizContent(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                               @PathVariable Long quizId) {
        User user = userService.authenticate(code);

        QuizContentResponse response = quizQueryService.readQuizContent(quizId, user);

        LOGGER.info("퀴즈 문제 조회 API: Get v1/quizzes/{}/content [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 루브릭 조회 API", description = "퀴즈의 루브릭을 조회한다. 퀴즈 정답을 제출한 적이 없으면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/rubric")
    public ResponseEntity<QuizRublicResponse> readQuizRubric(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                             @PathVariable Long quizId) {
        User user = userService.authenticate(code);

        QuizRublicResponse response = quizQueryService.readQuizRubric(quizId, user);

        LOGGER.info("퀴즈 루브릭 조회 API: Get v1/quizzes/{}/rubric [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 퀴즈 전체 내용 조회 API", description = "작성자가 본인인 퀴즈의 모든 정보를 조회한다.")
    @GetMapping("v1/my/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> readMyQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                   @PathVariable Long quizId) {
        User user = userService.authenticate(code);

        QuizResponse response = quizQueryService.readMyQuiz(quizId, user);

        LOGGER.info("내 퀴즈 전체 내용 조회 API: Get v1/quizzes/{} [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 작성한 퀴즈 주차별 개요 조회 API", description = "내가 참여한 챌린지에 주차별로 해당하는 작성한 퀴즈의 개요 목록을 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다."
            + "만약 주차가 0이거나 없으면 모든 퀴즈를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readMyQuizzes(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                   @PathVariable Long challengeId,
                                                                   @RequestParam(defaultValue = "0", required = false) Integer week) {
        User user = userService.authenticate(code);

        List<QuizSummaryResponse> responses = quizQueryService.readMyQuizzes(week, user, challengeId);

        LOGGER.info("내가 작성한 퀴즈 주차별 개요 조회 API: Get v1/challenges/{}/my/quizzes?week={} [User: {}, responses: {}]", challengeId, week, user.getId(), responses);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "주차별 내가 작성한 퀴즈 개수 조회 API", description = "내가 참여한 챌린지에 주차별로 해당하는 작성한 퀴즈의 개수를 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다. "
            + "만약 주차가 0이거나 없으면 총 제출 수를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes/count")
    public ResponseEntity<QuizCountResponse> countMyQuizzes(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                @PathVariable Long challengeId,
                                                                @RequestParam(defaultValue = "0", required = false) Integer week) {
        User user = userService.authenticate(code);

        QuizCountResponse response = quizQueryService.countMyQuizzes(week, user, challengeId);

        LOGGER.info("주차별 내가 작성한 퀴즈 개수 조회 API: Get v1/challenges/{}/my/quizzes/count?week={} [User: {}, count: {}]", challengeId, week, user.getId(), response.getCount());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 제출 API", description = "퀴즈를 제출한다. 내가 수강한 챌린지가 아니고, 현재 진행중인 챌린지가 아니라면 403 에러를 반환한다.")
//    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
//            @Content(mediaType = "application/json", schema = @Schema(implementation = CreateQuizRequest.class)),
//            @Content(mediaType = "multipart/form-data", array = @ArraySchema(schema = @Schema(implementation = MultipartFile.class, format = "binary")))
//    })
    @ApiResponse(responseCode = "201", description = "퀴즈 제출 성공")
    @PostMapping(path = "v1/challenges/{challengeId}/quizzes", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<QuizIdResponse> createQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                     @PathVariable Long challengeId,
                                                     @Valid @RequestPart CreateQuizRequest createQuizRequest,
                                                     @RequestPart(required = false) List<MultipartFile> quizFiles,
                                                     UriComponentsBuilder uriBuilder) {
        User user = userService.authenticate(code);
        Challenge challenge = takeAClassService.findOneTakedAndInProgress(challengeId, user);

        Quiz quiz = Quiz.of(createQuizRequest, user, challenge);
        quizService.createOne(quiz, quizFiles);

        // 챌린지 퀴즈 제출 조건 이상의 퀴즈를 제출했으면 열람 가능한 권한 생성
        readableService.createOneIfRight(challenge, user, quiz.getQuizWeek());
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quiz.getId()).toUri();

        LOGGER.info("퀴즈 제출 API: Post v1/challenges/{}/quizzes [User: {}, Quiz: {}]", challengeId, user.getId(), quiz.getId());
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quiz.getId()).build());
    }

    @Operation(summary = "퀴즈 정답 수정 API", description = "퀴즈의 정답/해설/출처/루브릭을 수정한다. 내가 작성한 퀴즈가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 수정 성공")
    @PatchMapping("v1/quizzes/{quizId}")
    public ResponseEntity<QuizIdResponse> updateQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                     @PathVariable Long quizId,
                                                     @RequestBody @Valid UpdateQuizRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        User user = userService.authenticate(code);

        quizService.updateAnswer(quizId, user, request);
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quizId).toUri();

        LOGGER.info("퀴즈 정답 수정 API: Patch v1/quizzes/{} [User: {}, quiz: {}]", quizId, user.getId(), quizId);
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }

    @Operation(summary = "퀴즈 풀기 정답 제출 API", description = "퀴즈를 푼다. 열람 가능한 주차의 문제가 아니거나, 이미 푼 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizIdResponse> solveQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid SolveRequest request,
                                                    UriComponentsBuilder uriBuilder) {
        User user = userService.authenticate(code);
        Quiz quiz = readableService.findOneInReadableWeek(quizId, user);
        solveService.checkNotSolved(quiz, user);                    // 이미 푼 문제인지 확인

        Solve solve = Solve.of(request, quiz, user);
        solveService.createOne(solve);                              // 정답 제출
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quiz.getId()).toUri();

        LOGGER.info("퀴즈 풀기 정답 제출 API: Post v1/quizzes/{}/solve [User: {}, quiz: {}]", quizId, user.getId(), quiz.getId());
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }

    @Operation(summary = "퀴즈 풀었던 정답 조회 API", description = "한 문제에 내가 제출한 정답을 조회한다.")
    @GetMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizSolvedAnswerResponse> readQuizSolvedAnswer(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                         @PathVariable Long quizId) {
        User user = userService.authenticate(code);                     // 회원 인증
        Solve solve = quizQueryService.findSolve(quizId, user);   // 제출한 정답 조회

        // 응답 객체 생성
        QuizSolvedAnswerResponse response = QuizSolvedAnswerResponse.builder()
                .quizId(solve.getQuiz().getId())
                .solveAnswer(solve.getSolveAnswer())
                .build();

        LOGGER.info("퀴즈 풀었던 정답 조회 API: Get v1/quizzes/{}/solve [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 풀기 채점 점수 제출 API", description = "퀴즈를 채점한다. 퀴즈 정답을 제출한 문제가 아니거나, 이미 푼 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PatchMapping(path = "v1/quizzes/{quizId}/grade")
    public ResponseEntity<QuizIdResponse> gradeQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid ScoreRequest request,
                                                    UriComponentsBuilder uriBuilder) {
        User user = userService.authenticate(code);
        Quiz quiz = readableService.findOneInReadableWeek(quizId, user);
        solveService.updateSolveScore(quiz, user, request.getScore());// 이미 푼 문제인지 확인
        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quiz.getId()).toUri();

        LOGGER.info("퀴즈 풀기 채점 점수 제출 API: Patch v1/quizzes/{}/grade [User: {}, quiz: {}]", quizId, user.getId(), quiz.getId());
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }
}
