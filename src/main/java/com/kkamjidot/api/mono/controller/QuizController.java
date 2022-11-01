package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.dto.response.*;
import com.kkamjidot.api.mono.service.*;
import com.kkamjidot.api.mono.service.query.QuizQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

@Tag(name = "퀴즈", description = "퀴즈 관련 작업들")
@RequiredArgsConstructor
@RestController
public class QuizController {
    private final QuizService quizService;
    private final QuizQueryService quizQueryService;
    private final SolveService solveService;
    private final TakeAClassService takeAClassService;

    @Operation(summary = "퀴즈 개요 목록 조회 API", description = "한 챌린지에 여러 주차에 해당하는 퀴즈의 개요 목록을 조회한다. 쿼리 week에는 여러 주차를 입력받는다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readQuizSummaries(HttpServletRequest request,
                                                                       @PathVariable Long challengeId,
                                                                       @RequestParam(required = false) List<Integer> week) throws MissingServletRequestParameterException {
//        if (week.isEmpty()) throw new org.springframework.web.bind.MissingServletRequestParameterException("week", "List");
        if (week == null || week.isEmpty()) week = new LinkedList<>();

        Long userId = (Long) request.getAttribute("userId");

        List<QuizSummaryResponse> responses = quizQueryService.readQuizSummaries(userId, challengeId, week);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "퀴즈 내용 조회 API", description = "퀴즈의 내용을 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/content")
    public ResponseEntity<QuizContentResponse> readQuizContent(HttpServletRequest request,
                                                               @PathVariable Long quizId) {
        Long userId = (Long) request.getAttribute("userId");

        QuizContentResponse response = quizQueryService.readQuizContent(quizId, userId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 정답 조회 API", description = "문제의 정답을 조회한다. 단, 정답을 제출한 적이 없으면 403 에러를 반환한다.")
    @GetMapping(path = "v2/quizzes/{quizId}/solve")
    public ResponseEntity<QuizAnswerResponse> readQuizAnswer(HttpServletRequest request,
                                                             @PathVariable Long quizId) {
        Long userId = (Long) request.getAttribute("userId");                     // 회원 인증
        solveService.checkSolved(quizId, userId);                // 퀴즈를 풀지 않았으면 403 에러
        return ResponseEntity.ok(quizQueryService.readQuizAnswer(quizId, userId));   // 제출한 정답 조회
    }

    @Operation(summary = "내 퀴즈 전체 내용 조회 API", description = "작성자가 본인인 퀴즈의 모든 정보를 조회한다.")
    @GetMapping("v1/my/quizzes/{quizId}")
    public ResponseEntity<MyQuizResponse> readMyQuiz(HttpServletRequest request,
                                                     @PathVariable Long quizId) {
        Long userId = (Long) request.getAttribute("userId");

        MyQuizResponse response = quizQueryService.readMyQuiz(quizId, userId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 작성한 퀴즈 주차별 개요 목록 조회 API", description = "내가 참여한 챌린지에 주차별로 해당하는 작성한 퀴즈의 개요 목록을 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다."
            + "만약 주차가 0이거나 없으면 모든 퀴즈를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readMyQuizSummaries(HttpServletRequest request,
                                                                         @PathVariable Long challengeId,
                                                                         @RequestParam(defaultValue = "0", required = false) Integer week) {
        Long userId = (Long) request.getAttribute("userId");

        List<QuizSummaryResponse> responses = quizQueryService.readMyQuizzes(week, userId, challengeId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "각 챌린지당 주차별 내가 작성한 퀴즈 개수 조회 API", description = "내가 참여한 챌린지에 주차별로 해당하는 작성한 퀴즈의 개수를 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다. "
            + "만약 주차가 0이거나 없으면 총 제출 수를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes/count")
    public ResponseEntity<QuizCountByChallengeResponse> countMyQuizzes(HttpServletRequest request,
                                                                       @PathVariable Long challengeId,
                                                                       @RequestParam(defaultValue = "0", required = false) Integer week) {
        Long userId = (Long) request.getAttribute("userId");

        QuizCountByChallengeResponse response = quizQueryService.countMyQuizzes(week, userId, challengeId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모든 챌린지에서 내가 작성한 퀴즈 주차별 개수 조회 API", description = "지금까지 제출한 퀴즈 개수를 주차별로 조회한다.")
    @GetMapping("v1/my/quizzes/count")
    public ResponseEntity<List<QuizTotalCountByWeekResponse>> countMyQuizzes(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        List<QuizTotalCountByWeekResponse> response = quizQueryService.countMyTotalQuizzes(userId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "퀴즈 제출 API", description = "퀴즈를 제출한다. 내가 수강한 챌린지가 아니고, 현재 진행중인 챌린지가 아니라면 403 에러를 반환한다.")
//    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
//            @Content(mediaType = "application/json", schema = @Schema(implementation = CreateQuizRequest.class)),
//            @Content(mediaType = "multipart/form-data", array = @ArraySchema(schema = @Schema(implementation = MultipartFile.class, format = "binary")))
//    })
    @ApiResponse(responseCode = "201", description = "퀴즈 제출 성공")
    @PostMapping(path = "v1/challenges/{challengeId}/quizzes", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<QuizIdResponse> createQuiz(HttpServletRequest request,
                                                     @PathVariable Long challengeId,
                                                     @Valid @RequestPart CreateQuizRequest createQuizRequest,
                                                     @RequestPart(required = false) List<MultipartFile> quizFiles,
                                                     UriComponentsBuilder uriBuilder) {
        Long userId = (Long) request.getAttribute("userId");

        takeAClassService.checkUserTakesChallengeAndInProgress(challengeId, userId);

        Long quizId = quizService.createOne(createQuizRequest, userId, challengeId, quizFiles);

        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quizId).toUri();
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }

    @Operation(summary = "퀴즈 정답 수정 API", description = "퀴즈의 정답/해설/출처/루브릭을 수정한다. 내가 작성한 퀴즈가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 수정 성공")
    @PatchMapping("v1/quizzes/{quizId}")
    public ResponseEntity<QuizIdResponse> updateQuiz(HttpServletRequest request,
                                                     @PathVariable Long quizId,
                                                     @RequestBody @Valid UpdateQuizRequest updateQuizRequest,
                                                     UriComponentsBuilder uriBuilder) {
        Long userId = (Long) request.getAttribute("userId");

        quizService.updateAnswer(quizId, userId, updateQuizRequest);

        URI location = uriBuilder.path("/v1/quizzes/{quizId}").buildAndExpand(quizId).toUri();
        return ResponseEntity.created(location).body(QuizIdResponse.builder().quizId(quizId).build());
    }
}
