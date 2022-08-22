package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "퀴즈 개수 응답")
public class QuizCountResponse {
    private final Integer count;
    private final Long challengeId;
    private final Integer week;
}
