package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Schema(name = "퀴즈 제출 현황 응답")
public class QuizSubmissionStatusResponse implements Serializable {
    private List<UserDto> userList;
    @Schema(description = "챌린지가 진행되는 주차 수") private Integer totalWeeks;
    @Schema(description = "Map<주차, 주차별 퀴즈 제출 현황>") private Map<Integer, QuizSubmissionStatusByWeek> quizSubmissionStatusByWeek;

    @Data
    @Schema(description = "주차별 퀴즈 제출 현황")
    static class QuizSubmissionStatusByWeek implements Serializable {
        @Schema(description = "Map<유저 ID, 제출 수>") private Map<Long, Integer> numberOfQuizzesSubmittedByUserId;
        @Schema(description = "최다 제출 수") private Integer mostNumberOfQuizzesSubmitted;
    }
}