package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.domain.enumerate.QuizCategory;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
@Entity(name = "Quiz")
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id", nullable = false)
    private Long id;

    @Column(name = "quiz_title", nullable = false, length = 50)
    private String quizTitle;

    @Column(name = "quiz_week", nullable = false)
    private Integer quizWeek;

    @Column(name = "quiz_content", nullable = false, length = 3500)
    private String quizContent;

    @Column(name = "quiz_answer", nullable = false, length = 3500)
    private String quizAnswer;

    @Column(name = "quiz_explanation", length = 3500)
    private String quizExplanation;

    @Column(name = "quiz_rubric", length = 3500)
    private String quizRubric;

    @Column(name = "quiz_source", length = 1000)
    private String quizSource;

    @Builder.Default
    @Column(name = "quiz_category", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory = QuizCategory.BASIC;

    @Column(name = "quiz_created_date", nullable = false)
    private LocalDateTime quizCreatedDate;

    @Column(name = "quiz_modified_date")
    private LocalDateTime quizModifiedDate;

    @Column(name = "quiz_deleted_date")
    private LocalDateTime quizDeletedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chall_id", nullable = false)
    private Challenge challenge;

    @OneToMany(mappedBy = "quiz")
    private Set<QuizFile> quizFiles = new LinkedHashSet<>();

    public String getWriterName() {
        return user.getUserName();
    }

    public Long getChallengeId() {
        return challenge.getId();
    }

    public static Quiz of(CreateQuizRequest request, User user, Challenge challenge) {
        return Quiz.builder()
                .quizTitle(request.getQuizTitle())
                .quizWeek(challenge.getNowWeek())
                .quizContent(request.getQuizContent())
                .quizAnswer(request.getQuizAnswer())
                .quizExplanation(request.getQuizExplanation())
                .quizRubric(request.getQuizRubric())
                .quizSource(request.getQuizSource())
//                .quizCategory(QuizCategory.BASIC)
                .user(user)
                .challenge(challenge)
                .build();
    }
}