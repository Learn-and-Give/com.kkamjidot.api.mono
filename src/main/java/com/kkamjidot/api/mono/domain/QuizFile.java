package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.dto.FileDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "quiz_id")
    @ToString.Exclude
    private Quiz quiz;

    public static QuizFile of(FileDto fileDto, Quiz quiz) {
        return QuizFile.builder()
                .qfName(fileDto.getName())
                .qfType(fileDto.getType())
                .qfPath(fileDto.getPath())
                .quiz(quiz)
                .build();
    }
}