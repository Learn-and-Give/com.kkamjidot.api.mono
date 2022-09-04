package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Schema(name = "퀴즈 풀기 요청")
public class SolveRequest implements Serializable {
    @Schema(description = "정답", example = "정답", required = true)
    @Size(min = 1, max = 4000, message = "정답은 1자 이상 4000자 이하여야 합니다.")
    private String answer;
}
