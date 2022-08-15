package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "챌린지 응답")
public class ChallengeResponse implements Serializable {
    @Schema(description = "챌린지 ID")
    private final Long id;

    @Schema(description = "챌린지 제목")
    private final String challengeInfoTitle;

    @Schema(description = "첼린지 설명(마크다운)")
    private final String challengeInfoDescription;

    @Schema(description = "챌린지가 진행되는 주차 수")
    private final Integer challengeInfoTotalWeeks;

    @Schema(description = "챌린지 참가비(없으면 0)")
    private final Integer challengeInfoCost;

    @Schema(description = "대상 대학")
    private final String challengeInfoUniversity;

    @Schema(description = "대상 학과")
    private final String challengeInfoDepartment;

    @Schema(description = "교수명")
    private final String challengeInfoProfessorName;

    @Schema(description = "챌린지 기수")
    private final Integer challengeChapter;

    @Schema(description = "챌린지 대상")
    private final String challengeTarget;

    @Schema(description = "챌린지 시작일시")
    private final LocalDateTime challengeStartDate;

    @Schema(description = "챌린지 종료일시")
    private final LocalDateTime challengeEndDate;

    @Schema(description = "신청시작일시")
    private final LocalDateTime challengeApplicationStartDate;

    @Schema(description = "신청종료일시")
    private final LocalDateTime challengeApplicationEndDate;

    @Schema(description = "기수 별 상세 설명")
    private final String challengeDetail;

    @Schema(description = "챌린지 이미지 경로")
    private final String challengeInfoImageUrl;

    @Schema(description = "챌린지 생성일시")
    private LocalDateTime createdDate;

    @Schema(description = "수정일시(수정 안 됐으면 null)")
    private LocalDateTime modifiedDate;

    @Schema(description = "내가 참여한 챌린지 여부")
    private boolean isApplied;
}
