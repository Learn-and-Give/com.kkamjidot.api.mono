package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@Builder
@Schema(name = "퀴즈 평가 응답")
public class QuizRateResponse implements Serializable {
    @Schema(description = "평가 내용", example = "0", required = true)
    private final Integer cntOfGood;

    @Schema(description = "퀴즈 ID", example = "0", required = true)
    private final Long quizId;
}
