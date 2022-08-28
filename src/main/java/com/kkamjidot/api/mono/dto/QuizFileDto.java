package com.kkamjidot.api.mono.dto;

import com.kkamjidot.api.mono.domain.QuizFile;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
public class QuizFileDto implements Serializable {
    private final Long id;
    private final String qfName;
    private final String qfType;
    private final String qfPath;
    private final LocalDateTime qfCreatedDate;
    private final LocalDateTime qfModifiedDate;
    private final LocalDateTime qfDeletedDate;

    public static QuizFileDto of(QuizFile quizFile) {
        return QuizFileDto.builder()
                .id(quizFile.getId())
                .qfName(quizFile.getQfName())
                .qfType(quizFile.getQfType())
                .qfPath(quizFile.getQfPath())
                .qfCreatedDate(quizFile.getQfCreatedDate())
                .qfModifiedDate(quizFile.getQfModifiedDate())
                .qfDeletedDate(quizFile.getQfDeletedDate())
                .build();
    }
}
