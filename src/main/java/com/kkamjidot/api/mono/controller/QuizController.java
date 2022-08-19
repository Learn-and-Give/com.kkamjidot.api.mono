package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.SolveRequest;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "퀴즈", description = "퀴즈 관련 작업들")
@RestController
public class QuizController {
    @Operation(summary = "퀴즈 개요 목록 조회 API(쿼리 week)", description = "한 챌린지에 한 주차에 해당하는 퀴즈의 개요 목록을 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readQuizSummaries(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                       @PathVariable Long challengeId,
                                                                       @RequestParam Integer week) {
        return null;
    }

    @Operation(summary = "퀴즈 문제 조회 API", description = "퀴즈의 문제 내용을 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/content")
    public ResponseEntity<QuizContentResponse> readQuizContent(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                               @PathVariable String quizId) {
        return null;
    }

    @Operation(summary = "퀴즈 상세 내용 조회 API", description = "퀴즈의 정답을 포함한 모든 정보를 조회한다. 열람 가능 주차가 아니면 403 에러를 반환한다. 단, 작성자가 본인일 경우 열람 가능하다.")
    @GetMapping("v1/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> readQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                 @PathVariable String quizId) {
        return null;
    }

    @Operation(summary = "내가 작성한 퀴즈 개요 조회 API", description = "내가 참여한 챌린지에 해당하는 내가 작성한 퀴즈의 개요 목록을 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/challenges/{challengeId}/my/quizzes")
    public ResponseEntity<List<QuizSummaryResponse>> readMyQuizzes(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                                   @PathVariable String challengeId) {
        return null;
    }

    @Operation(summary = "퀴즈 제출 API", description = "퀴즈를 제출한다. 내가 수강한 챌린지가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 제출 성공")
    @PostMapping(path = "v1/challenges/{challengeId}/quizzes", consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})  // {MediaType.APPLICATION_JSON_VALUE, }
    public ResponseEntity<QuizIdResponse> createQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                     @PathVariable String challengeId,
                                                     @Valid @RequestPart CreateQuizRequest createQuizRequest,
                                                     @RequestPart(required = false) MultipartFile[] quizFiles) {
        return null;
    }

    @Operation(summary = "퀴즈 정답 수정 API", description = "퀴즈의 정답/해설/출처/루브릭을 수정한다. 내가 작성한 퀴즈가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 수정 성공")
    @PatchMapping("v1/quizzes/{quizId}")
    public ResponseEntity<QuizIdResponse> updateQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                     @PathVariable String quizId,
                                                     @RequestBody @Valid UpdateQuizRequest request) {
        return null;
    }

    @Operation(summary = "퀴즈 풀기 API", description = "퀴즈를 푼다. 열람 가능한 주차의 문제만 풀 수 있고, 이미 푼 문제는 다시 풀 수 없다.")
    @ApiResponse(responseCode = "201", description = "퀴즈 풀기 성공")
    @PostMapping(path = "v1/quizzes/{quizId}/solve")
    public ResponseEntity<SolveResponse> solveQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                      @PathVariable String quizId,
                                      @RequestBody @Valid SolveRequest request) {
        return null;
    }
}
