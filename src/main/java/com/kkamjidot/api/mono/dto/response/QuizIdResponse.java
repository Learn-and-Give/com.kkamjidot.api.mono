package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@Schema(name = "퀴즈 ID 응답")
public class QuizIdResponse implements Serializable {
    private final Long quizId;
}
