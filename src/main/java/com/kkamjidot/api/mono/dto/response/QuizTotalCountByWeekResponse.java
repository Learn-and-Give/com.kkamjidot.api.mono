package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "주차별 총 퀴즈 수 응답")
public class QuizTotalCountByWeekResponse {
    private final Integer week;
    private final Long count;

    public QuizTotalCountByWeekResponse(Integer week, Long count) {
        this.week = week;
        this.count = count;
    }
}
