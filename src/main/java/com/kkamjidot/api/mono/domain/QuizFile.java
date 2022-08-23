package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "QuizFile")
@Table(name = "quiz_file")
public class QuizFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qf_id", nullable = false)
    private Long id;

    @Column(name = "qf_name", nullable = false)
    private String qfName;

    @Column(name = "qf_type", length = 50)
    private String qfType;

    @Column(name = "qf_path", nullable = false)
    private String qfPath;

    @Column(name = "qf_created_date")
    private LocalDateTime qfCreatedDate;

    @Column(name = "qf_modified_date")
    private LocalDateTime qfModifiedDate;

    @Column(name = "qf_deleted_date")
    private LocalDateTime qfDeletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

}