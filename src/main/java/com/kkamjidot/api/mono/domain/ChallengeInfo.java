package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "ChallengeInfo")
@Table(name = "challenge_info")
public class ChallengeInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_info_id", nullable = false)
    private Long id;

    @Column(name = "challenge_info_title", nullable = false)
    private String challengeInfoTitle;

    @Lob
    @Column(name = "challenge_info_description")
    private String challengeInfoDescription;

    @Column(name = "challenge_info_total_weeks", nullable = false)
    private Integer challengeInfoTotalWeeks;

    @Column(name = "challenge_info_cost")
    private Integer challengeInfoCost;

    @Column(name = "challenge_info_university")
    private String challengeInfoUniversity;

    @Column(name = "challenge_info_department")
    private String challengeInfoDepartment;

    @Column(name = "challenge_info_professor_name")
    private String challengeInfoProfessorName;

    @Column(name = "challenge_info_image_url")
    private String challengeInfoImageUrl;

}