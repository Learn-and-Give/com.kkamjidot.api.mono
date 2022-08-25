package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "현재 주차 반환 응답")
public class ThisWeekResponse implements Serializable {
    private final Long week;
    private final Long challengeId;
    private final LocalDateTime challStartDate;
    private final LocalDateTime now;
}
