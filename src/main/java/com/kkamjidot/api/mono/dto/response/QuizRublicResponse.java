package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@Schema(name = "퀴즈 루브릭 응답")
public class QuizRublicResponse {
    private final Long quizId;
    private final String quizRubric;
}
