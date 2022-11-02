package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.Comment;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.CreateCommentRequest;
import com.kkamjidot.api.mono.dto.response.CommentIdResponse;
import com.kkamjidot.api.mono.dto.response.CommentResponse;
import com.kkamjidot.api.mono.service.*;
import com.kkamjidot.api.mono.service.query.CommentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "댓글", description = "댓글 관련 작업들")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentQueryService commentQueryService;
    private final TakeAClassService takeAClassService;

    @Operation(summary = "댓글 등록 API", description = "댓글을 등록한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "댓글 등록 성공")
    @PostMapping("v1/quizzes/{quizId}/comments")
    public ResponseEntity<CommentIdResponse> createComment(HttpServletRequest request,
                                                           @PathVariable Long quizId,
                                                           @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        Long userId = (Long) request.getAttribute("userId");
        takeAClassService.checkCanReadChallengeByQuizId(quizId, userId);
//        Quiz quiz = quizService.findOneInReadableWeek(quizId, user);     // 열람 가능한 주차의 문제인지 확인

        Long commentId = commentService.createOne(createCommentRequest.getContent(), userId, quizId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(CommentIdResponse.builder().commentId(commentId).build());
    }

    @Operation(summary = "댓글 목록 조회 API", description = "한 퀴즈의 댓글들을 조회한다. 내가 수강한 챌린지가 아니면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/comments")
    public ResponseEntity<List<CommentResponse>> readComments(HttpServletRequest request,
                                                             @PathVariable Long quizId) {
        Long userId = (Long) request.getAttribute("userId");
        takeAClassService.checkCanReadChallengeByQuizId(quizId, userId);
        List<CommentResponse> responses = commentQueryService.readComments(userId, quizId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제한다. 내 댓글이 아니면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @DeleteMapping("v1/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(HttpServletRequest request,
                                                             @PathVariable Long commentId) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.deleteOne(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
