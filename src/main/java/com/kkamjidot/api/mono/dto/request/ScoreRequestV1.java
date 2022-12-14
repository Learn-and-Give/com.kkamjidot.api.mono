package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "퀴즈 채점 요청 V1")
public class ScoreRequestV1 {
    @Schema(description = "점수", example = "0", required = true)
    private Integer score;
}
