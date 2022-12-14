package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@Schema(name = "챌린지 별 퀴즈 수 응답")
public class QuizCountByChallengeResponse {
    private final Integer count;
    private final Long challengeId;
    private final Integer week;
}
