package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@Schema(name = "퀴즈 풀기 응답")
public class SolveResponse implements Serializable {
    private final Long quizId;
}
