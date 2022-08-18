package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "댓글 조회 응답")
public class CommentResponse implements Serializable {
    private final Long commentId;
    private final String commentContent;
    private final LocalDateTime commentCreatedDate;
    private final LocalDateTime commentModifiedDate;
    private final Boolean isMine;
}
