package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Comment;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Builder
@Schema(name = "댓글 조회 응답")
public class CommentResponse implements Serializable {
    private final Long commentId;
    private final String commentContent;
    private final LocalDateTime commentCreatedDate;
    private final LocalDateTime commentModifiedDate;
    private final Boolean isQuizWriter;
    private final Boolean isMine;
    private final String writerName;
    private final Long quizId;

    public static List<CommentResponse> listOf(List<Comment> commentList, User user, Quiz quiz) {
        return commentList.stream()
                .map(comment -> CommentResponse.of(comment, user, quiz))
                .toList();
    }

    private static CommentResponse of(Comment comment, User user, Quiz quiz) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .commentContent(comment.getCommentContent())
                .commentCreatedDate(comment.getCommentCreatedDate())
                .commentModifiedDate(comment.getCommentModifiedDate())
                .isQuizWriter(comment.isMine(quiz.getUser()))
                .isMine(comment.isMine(user))
                .writerName(comment.getUser().getUserName())
                .quizId(comment.getQuiz().getId())
                .build();
    }
}
