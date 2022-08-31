package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.dto.request.CreateCommentRequest;
import com.kkamjidot.api.mono.dto.response.CommentIdResponse;
import com.kkamjidot.api.mono.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "댓글", description = "댓글 관련 작업들")
@RequiredArgsConstructor
@RestController
public class CommentController {
    @Operation(summary = "개발 중)댓글 등록 API", description = "댓글을 등록한다. 열람 가능한 퀴즈가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "댓글 등록 성공")
    @PostMapping("v1/quizzes/{quizId}/comments")
    public ResponseEntity<CommentIdResponse> createQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                        @PathVariable String quizId,
                                                        @RequestBody @Valid CreateCommentRequest request) {
        return null;
    }

    @Operation(summary = "개발 중)댓글 목록 조회 API", description = "한 퀴즈의 댓글들을 조회한다. 열람 가능한 퀴즈가 아니라면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/comments")
    public ResponseEntity<List<CommentResponse>> readQuizzes(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                             @PathVariable String quizId) {
        return null;
    }

    @Operation(summary = "개발 중)댓글 삭제 API", description = "댓글을 삭제한다. 내 댓글이 아니면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @DeleteMapping("v1/comments/{commentId}")
    public ResponseEntity<Void> deleteQuiz(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                             @PathVariable String commentId) {
        return ResponseEntity.noContent().build();
    }
}
