package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "퀴즈 개요 응답")
public class QuizSummaryResponse implements Serializable {
    private final Long quizId;
    private final String title;
    private final String category;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final Boolean isMine;
    private final Boolean isSolved;
    private final String writerName;
    private final Integer week;
    private final Long challengeId;
}
