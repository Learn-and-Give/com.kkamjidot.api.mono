package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.repository.QuizRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@Entity(name = "Challenge")
@Table(name = "challenge")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chall_id", nullable = false)
    private Long id;

    @Column(name = "chall_chapter", nullable = false)
    private Integer challChapter;

    @Column(name = "chall_target", nullable = false)
    private String challTarget;

    @Column(name = "chall_start_date", nullable = false)
    private LocalDateTime challStartDate;

    @Column(name = "chall_end_date", nullable = false)
    private LocalDateTime challEndDate;

    @Column(name = "chall_application_start_date")
    private LocalDateTime challApplicationStartDate;

    @Column(name = "chall_application_end_date")
    private LocalDateTime challApplicationEndDate;

    @Column(name = "chall_total_weeks", nullable = false)
    private Integer challTotalWeeks;

    @Column(name = "chall_min_num_of_quizzes_by_week", nullable = false)
    private Integer challMinNumOfQuizzesByWeek;

    @Column(name = "chall_cost", nullable = false)
    private Integer challCost;

    @Column(name = "chall_detail", length = 4000)
    private String challDetail;

    @Column(name = "chall_created_date")
    private LocalDateTime challCreatedDate;

    @Column(name = "chall_modified_date")
    private LocalDateTime challModifiedDate;

    @Column(name = "chall_deleted_date")
    private LocalDateTime challDeletedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cinfo_id", nullable = false)
    private ChallengeInfo challengeInfo;

    @OneToMany(mappedBy = "challenge")
    private Set<Quiz> quizzes = new LinkedHashSet<>();

    public Integer getNowWeek() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Math.toIntExact(ChronoUnit.DAYS.between(this.challStartDate, now) / 7 + 1);        // 오늘 주차
    }

    public boolean isInProgress() {
        return this.getChallEndDate().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }

    public boolean isCountOfQuizzesIsEnough(int count) {
        return getChallMinNumOfQuizzesByWeek() > count;
    }
}