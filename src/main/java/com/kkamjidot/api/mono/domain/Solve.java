package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.dto.request.SolveRequest;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@Entity(name = "Solve")
@Table(name = "solve")
public class Solve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`solve _id`", nullable = false)
    private Long id;

    @Column(name = "solve_answer", nullable = false, length = 4000)
    private String solveAnswer;

    @Column(name = "solve_score")
    private Integer solveScore;

    @Column(name = "solve_created_date", nullable = false)
    private LocalDateTime solveCreatedDate;

    @Column(name = "solve_modified_date")
    private LocalDateTime solveModifiedDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Solve empty() {
        return Solve.builder().build();
    }

    public static Solve of(SolveRequest request, Quiz quiz, User user) {
        return Solve.builder()
                .solveAnswer(request.getAnswer())
                .quiz(quiz)
                .user(user)
                .build();
    }

    public void setSolveScore(Integer solveScore) {
        this.solveScore = solveScore;
    }
}