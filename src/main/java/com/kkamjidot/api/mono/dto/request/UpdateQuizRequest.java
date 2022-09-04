package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Schema(name = "퀴즈 수정 요청")
public class UpdateQuizRequest implements Serializable {
    @Schema(description = "모범 답안", example = "모범 답안")
    @Size(max = 3500, message = "모범 답안은 1자 이상 3500자 이하여야 합니다.")
    private String quizAnswer;

    @Schema(description = "해설", example = "해설")
    @Size(max = 3500, message = "해설은 3500자 이하여야 합니다.")
    private String quizExplanation;

    @Schema(description = "루브릭", example = "루브릭")
    @Size(max = 3500, message = "루브릭은 3500자 이하여야 합니다.")
    private String quizRubric;

    @Schema(description = "출처", example = "출처")
    @Size(max = 1000, message = "출처는 1000자 이하여야 합니다.")
    private String quizSource;
}
