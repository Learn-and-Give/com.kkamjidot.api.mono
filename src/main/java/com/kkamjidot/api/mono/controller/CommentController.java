package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.domain.Comment;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.CreateCommentRequest;
import com.kkamjidot.api.mono.dto.response.CommentIdResponse;
import com.kkamjidot.api.mono.dto.response.CommentResponse;
import com.kkamjidot.api.mono.service.CommentService;
import com.kkamjidot.api.mono.service.QuizService;
import com.kkamjidot.api.mono.service.UserService;
import com.kkamjidot.api.mono.service.query.CommentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "댓글", description = "댓글 관련 작업들")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final Logger LOGGER = LoggerFactory.getLogger(ChallengeController.class);

    private final UserService userService;
    private final QuizService quizService;
    private final CommentService commentService;
    private final CommentQueryService commentQueryService;

    @Operation(summary = "댓글 등록 API", description = "댓글을 등록한다. 열람 가능한 퀴즈가 아니라면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "201", description = "댓글 등록 성공")
    @PostMapping("v1/quizzes/{quizId}/comments")
    public ResponseEntity<CommentIdResponse> createComment(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                        @PathVariable Long quizId,
                                                        @RequestBody @Valid CreateCommentRequest request) {
        User user = userService.authenticate_deprecated(code);
        Quiz quiz = quizService.findOneInReadableWeek(quizId, user);     // 열람 가능한 주차의 문제인지 확인

        Comment comment = Comment.builder()
                .commentContent(request.getContent())
                .user(user)
                .quiz(quiz)
                .build();
        commentService.createOne(comment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        LOGGER.info("댓글 등록 API: Post v1/quizzes/{}/comments [User: {}, comment: {}]", quizId, user.getId(), comment.getId());
        return ResponseEntity.created(location).body(CommentIdResponse.builder().commentId(comment.getId()).build());
    }

    @Operation(summary = "댓글 목록 조회 API", description = "한 퀴즈의 댓글들을 조회한다. 열람 가능한 퀴즈가 아니라면 403 에러를 반환한다.")
    @GetMapping("v1/quizzes/{quizId}/comments")
    public ResponseEntity<List<CommentResponse>> readComments(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                             @PathVariable Long quizId) {
        User user = userService.authenticate_deprecated(code);
        Quiz quiz = quizService.findOneInReadableWeek(quizId, user);
        List<CommentResponse> responses = commentQueryService.readComments(user, quiz);

        LOGGER.info("댓글 목록 조회 API: Get v1/quizzes/{}/comments [User: {}, responses: {}]", quizId, user.getId(), responses);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제한다. 내 댓글이 아니면 403 에러를 반환한다.")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @DeleteMapping("v1/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@Parameter(description = "로그인한 회원 코드", example = "1234") @RequestHeader String code,
                                                             @PathVariable Long commentId) {
        User user = userService.authenticate_deprecated(code);
        commentService.deleteOne(commentId, user);
        LOGGER.info("댓글 삭제 API: Delete v1/comments/{} [User: {}]", commentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
