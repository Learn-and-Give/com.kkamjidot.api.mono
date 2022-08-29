package com.kkamjidot.api.mono.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Solve empty() {
        return Solve.builder().build();
    }
}