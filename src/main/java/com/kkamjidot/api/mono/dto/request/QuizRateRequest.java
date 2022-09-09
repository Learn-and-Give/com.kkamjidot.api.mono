package com.kkamjidot.api.mono.dto.request;

import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@ToString
@Schema(name = "퀴즈 평가 요청")
public class QuizRateRequest implements Serializable {
    @Schema(description = "평가 내용(BAD, GOOD)", example = "GOOD", required = true)
    private RateValue rate;
}
