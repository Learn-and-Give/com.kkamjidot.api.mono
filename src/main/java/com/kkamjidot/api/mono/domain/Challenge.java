package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "Challenge")
@Table(name = "challenge")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id", nullable = false)
    private Long id;

    @Column(name = "challenge_chapter", nullable = false)
    private Integer challengeChapter;

    @Column(name = "challenge_target")
    private String challengeTarget;

    @Column(name = "challenge_start_date")
    private LocalDateTime challengeStartDate;

    @Column(name = "challenge_end_date")
    private LocalDateTime challengeEndDate;

    @Column(name = "challenge_application_start_date")
    private LocalDateTime challengeApplicationStartDate;

    @Column(name = "challenge_application_end_date")
    private LocalDateTime challengeApplicationEndDate;

    @Lob
    @Column(name = "challenge_detail")
    private String challengeDetail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_info_id", nullable = false)
    private ChallengeInfo challengeInfo;

}