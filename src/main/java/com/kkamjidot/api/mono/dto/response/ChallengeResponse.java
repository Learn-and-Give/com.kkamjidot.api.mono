package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.ChallengeInfo;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
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

    @Schema(description = "주차별 최소 제출 문제 수")
    private final Integer minNumOfQuizzesByWeek;

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
    private final LocalDateTime createdDate;

    @Schema(description = "수정일시(수정 안 됐으면 null)")
    private final  LocalDateTime modifiedDate;

    @Schema(description = "챌린지 신청 상태")
    private ApplicationStatus applicationStatus;

    @Schema(description = "현재 주차 미션 성공 챌린지원 수") private int numberOfChallengerWhoCompleted;

    public void setApplicationStatus(ApplicationStatus status) {
        this.applicationStatus = status;
    }

    public static ChallengeResponse of(Challenge challenge, int numberOfChallengerWhoCompleted) {
        ChallengeInfo challengeInfo = challenge.getChallengeInfo();
        return ChallengeResponse.builder()
                .challengeId(challenge.getId())
                .title(challengeInfo.getCinfoTitle())
                .description(challengeInfo.getCinfoDescription())
                .totalWeeks(challenge.getChallTotalWeeks())
                .minNumOfQuizzesByWeek(challenge.getChallMinNumOfQuizzesByWeek())
                .cost(challenge.getChallCost())
                .university(challengeInfo.getCinfoUniversity())
                .department(challengeInfo.getCinfoDepartment())
                .professorName(challengeInfo.getCinfoProfessorName())
                .chapter(challenge.getChallChapter())
                .target(challenge.getChallTarget())
                .startDate(challenge.getChallStartDate())
                .endDate(challenge.getChallEndDate())
                .applicationStartDate(challenge.getChallApplicationStartDate())
                .applicationEndDate(challenge.getChallApplicationEndDate())
                .detail(challenge.getChallDetail())
                .imageUrl(challengeInfo.getCinfoImageUrl())
                .createdDate(challenge.getChallCreatedDate())
                .modifiedDate(challenge.getChallModifiedDate())
                .numberOfChallengerWhoCompleted(numberOfChallengerWhoCompleted)
                .build();
    }
}
