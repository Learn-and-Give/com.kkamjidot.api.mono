package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.*;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.GradeRequest;
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

import javax.validation.Valid;
import java.util.List;

@Tag(name = "퀴즈", description = "퀴즈 관련 작업들")
@RequiredArgsConstructor
@RestController
public class QuizController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final ChallengeService challengeService;
    private final QuizService quizService;
    private final QuizQueryService quizQueryService;
    private final TakeAClassService takeAClassService;

    @Operation(summary = "개발 중)퀴즈 개요 목록 조회 API(쿼리 week)", description = "한 챌린지에 여러 주차에 해당하는 퀴즈의 개요 목록을 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다." +
            "쿼리 week에는 여러 주차를 구분자 콤마로 구분하여 입력받는다.")
    @GetMapping("v1/challenges/{challengeId}/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readQuizSummaries(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                       @PathVariable Long challengeId,
                                                                       @RequestParam int[] week) {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>{}", week);
        return null;
    }

    @Operation(summary = "퀴즈 문제 조회 API", description = "퀴즈의 문제 내용을 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다. 단, 작성자가 본인일 경우 열람 가능하다.")
    @GetMapping("v1/quizzes/{quizId}/content")
    public ResponseEntity<QuizContentResponse> readQuizContent(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                               @PathVariable Long quizId) {
        User user = userService.authenticate(code);     // 회원 인증

        QuizContentResponse response = quizQueryService.findContent(quizId, user);  // 퀴즈 문제 조회

        LOGGER.info("퀴즈 문제 조회 API: Get v1/quizzes/{}/content [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 루브릭 조회 API", description = "퀴즈의 루브릭을 조회한다. 퀴즈 정답을 제출한 적이 없으면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/rubric")
    public ResponseEntity<QuizRublicResponse> readQuizRubric(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                             @PathVariable Long quizId) {
        User user = userService.authenticate(code);     // 회원 인증

        QuizRublicResponse response = quizQueryService.findRublic(quizId, user);    // 루브릭 조회

        LOGGER.info("퀴즈 루브릭 조회 API: Get v1/quizzes/{}/rubric [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 퀴즈 전체 내용 조회 API", description = "작성자가 본인인 퀴즈의 모든 정보를 조회한다.")
    @GetMapping("v1/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> readMyQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                   @PathVariable Long quizId) {
        User user = userService.authenticate(code);     // 회원 인증

        QuizResponse response = quizQueryService.findMine(quizId, user);  // 내 퀴즈 조회

        LOGGER.info("내 퀴즈 전체 내용 조회 API: Get v1/quizzes/{} [User: {}, response: {}]", quizId, user.getId(), response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 작성한 퀴즈 주차별 개요 조회 API(쿼리 week)", description = "내가 참여한 챌린지에 주차별로 해당하는 작성한 퀴즈의 개요 목록을 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다."
            + "만약 주차가 0이거나 없으면 모든 퀴즈를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readMyQuizzes(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                   @PathVariable Long challengeId,
                                                                   @RequestParam(defaultValue = "0", required = false) Integer week) {
        User user = userService.authenticate(code);  // 회원 인증

        List<QuizSummaryResponse> responses = quizQueryService.findAllMine(week, user, challengeId);  // 내 퀴즈 리스트 조회

        LOGGER.info("내가 작성한 퀴즈 주차별 개요 조회 API: Get v1/challenges/{}/my/quizzes?week={} [User: {}, responses: {}]", challengeId, week, user.getId(), responses);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "주차별 내가 작성한 퀴즈 개수 조회 API(쿼리 week)", description = "내가 참여한 챌린지에 주차별로 해당하는 작성한 퀴즈의 개수를 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다. "
            + "만약 주차가 0이거나 없으면 총 제출 수를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes/count")
    public ResponseEntity<QuizCountResponse> readMyQuizzesCount(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                @PathVariable Long challengeId,
                                                                @RequestParam(defaultValue = "0", required = false) Integer week) {
        User user = userService.authenticate(code);  // 회원 인증

        QuizCountResponse response = quizQueryService.countAllMine(week, user, challengeId);  // 퀴즈 제출 개수 조회

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
                                                     @RequestPart(required = false) List<MultipartFile> quizFiles) {
        User user = userService.authenticate(code);  // 회원 인증
        Challenge challenge = takeAClassService.authenticateProgress(challengeId, user);  // 수강 신청 상태 확인

        Quiz quiz = Quiz.of(createQuizRequest, user, challenge);
        quizService.createOne(quiz, quizFiles);  // 퀴즈 생성

        LOGGER.info("퀴즈 제출 API: Post v1/challenges/{}/quizzes [User: {}, Quiz: {}]", challengeId, user.getId(), quiz.getId());
        return ResponseEntity.ok(QuizIdResponse.builder().quizId(quiz.getId()).build());
    }

    @Operation(summary = "개발 중)퀴즈 정답 수정 API", description = "퀴즈의 정답/해설/출처/루브릭을 수정한다. 내가 작성한 퀴즈가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 수정 성공")
    @PatchMapping("v1/quizzes/{quizId}")
    public ResponseEntity<QuizIdResponse> updateQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                     @PathVariable Long quizId,
                                                     @RequestBody @Valid UpdateQuizRequest request) {
//        User user = userService.authorization(code);  // 회원 인증
//
//        quizService.updateOne(quizId, user, request);
//
//        LOGGER.info("퀴즈 정답 수정 API: Patch v1/quizzes/{} [User: {}]", quizId, user.getId());
//        return ResponseEntity.ok(QuizIdResponse.builder().quizId(quizId).build());
        return null;
    }

    @Operation(summary = "개발 중)퀴즈 풀기 정답 제출 API", description = "퀴즈를 푼다. 열람 가능한 주차의 문제가 아니거나, 이미 푼 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizIdResponse> solveQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid SolveRequest request) {
        User user = userService.authenticate(code);  // 회원 인증

        return null;
    }

    @Operation(summary = "개발 중)퀴즈 풀었던 정답 조회 API", description = "한 문제에 내가 제출한 정답을 조회한다.")
    @GetMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<QuizSolvedAnswerResponse> readQuizSolvedAnswer(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                         @PathVariable Long quizId) {
        return null;
    }

    @Operation(summary = "개발 중)퀴즈 풀기 채점 점수 제출 API", description = "퀴즈를 채점한다. 퀴즈 정답을 제출한 문제가 아니거나, 이미 푼 문제면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/grade")
    public ResponseEntity<QuizIdResponse> gradeQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                    @PathVariable Long quizId,
                                                    @RequestBody @Valid GradeRequest request) {
        return null;
    }
}
