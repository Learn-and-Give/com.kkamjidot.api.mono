package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "ChallengeInfo")
@Table(name = "challenge_info")
public class ChallengeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinfo_id", nullable = false)
    private Long id;

    @Column(name = "cinfo_title", nullable = false)
    private String cinfoTitle;

    @Column(name = "cinfo_description", length = 4000)
    private String cinfoDescription;

    @Column(name = "cinfo_total_weeks", nullable = false)
    private Integer cinfoTotalWeeks;

    @Column(name = "cinfo_university", nullable = false)
    private String cinfoUniversity;

    @Column(name = "cinfo_department", nullable = false)
    private String cinfoDepartment;

    @Column(name = "cinfo_professor_name", nullable = false)
    private String cinfoProfessorName;

    @Column(name = "cinfo_image_url")
    private String cinfoImageUrl;

    @Column(name = "cinfo_created_date")
    private LocalDateTime cinfoCreatedDate;

    @Column(name = "cinfo_modified_date")
    private LocalDateTime cinfoModifiedDate;

    @Column(name = "cinfo_deleted_date")
    private LocalDateTime cinfoDeletedDate;
}