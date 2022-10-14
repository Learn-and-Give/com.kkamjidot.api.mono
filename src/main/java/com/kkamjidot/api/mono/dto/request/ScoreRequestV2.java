package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Schema(name = "퀴즈 채점 요청 V2")
public class ScoreRequestV2 {
    @Schema(description = "점수", example = "0", required = true)
    private Integer score;

    @NotBlank @Size(max = 3500)
    @Schema(description = "선택된 루브릭", example = "선택된 루브릭 내용입니다.", required = true)
    private String chosenRubric;
}
