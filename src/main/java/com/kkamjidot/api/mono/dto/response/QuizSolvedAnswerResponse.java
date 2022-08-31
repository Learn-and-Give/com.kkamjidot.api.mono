package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@Schema(name = "퀴즈 풀었던 정답 응답")
public class QuizSolvedAnswerResponse {
    private final Long quizId;
    private final String solveAnswer;
}
