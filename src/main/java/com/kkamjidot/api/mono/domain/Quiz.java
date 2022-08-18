package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Quiz")
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id", nullable = false)
    private Long id;

    @Column(name = "quiz_title", length = 50)
    private String quizTitle;

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

    @Column(name = "quiz_category", nullable = false, length = 50)
    private String quizCategory;

    @Column(name = "quiz_created_date")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;

}