package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@Schema(name = "주차 정보 조회 응답")
public class WeekResponse implements Serializable {
    private final Long challengeId;
    private final Integer totalWeeks;
    private final boolean[] weeks;
}
