package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "챌린지 응답")
public class ChallengeResponse implements Serializable {
    @Schema(description = "챌린지 ID")
    private final Long challengeId;

    @Schema(description = "챌린지 제목")
    private final String title;

    @Schema(description = "첼린지 설명(마크다운)")
    private final String description;

    @Schema(description = "챌린지가 진행되는 주차 수")
    private final Integer totalWeeks;

    @Schema(description = "챌린지 참가비(없으면 0)")
    private final Integer cost;

    @Schema(description = "대상 대학")
    private final String university;

    @Schema(description = "대상 학과")
    private final String department;

    @Schema(description = "교수명")
    private final String professorName;

    @Schema(description = "챌린지 기수")
    private final Integer chapter;

    @Schema(description = "챌린지 대상")
    private final String target;

    @Schema(description = "챌린지 시작일시")
    private final LocalDateTime startDate;

    @Schema(description = "챌린지 종료일시")
    private final LocalDateTime endDate;

    @Schema(description = "신청시작일시")
    private final LocalDateTime applicationStartDate;

    @Schema(description = "신청종료일시")
    private final LocalDateTime applicationEndDate;

    @Schema(description = "기수 별 상세 설명")
    private final String detail;

    @Schema(description = "챌린지 이미지 경로")
    private final String imageUrl;

    @Schema(description = "챌린지 생성일시")
    private LocalDateTime createdDate;

    @Schema(description = "수정일시(수정 안 됐으면 null)")
    private LocalDateTime modifiedDate;

    @Schema(description = "내가 참여한 챌린지 여부")
    private Boolean isParticipated;
}
