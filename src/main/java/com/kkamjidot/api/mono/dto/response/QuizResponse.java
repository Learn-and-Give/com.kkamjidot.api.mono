package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.dto.QuizFileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(name = "퀴즈 응답")
public class QuizResponse implements Serializable {
    private final Long quizId;
    private final String quizTitle;
    private final Integer quizWeek;
    private final String quizCategory;
    private final String quizContent;
    private final String quizAnswer;
    private final String quizExplanation;
    private final String quizRubric;
    private final String quizSource;
    private final LocalDateTime quizCreatedDate;
    private final LocalDateTime quizModifiedDate;
    private final Boolean isMine;
    private final Boolean isSolved;
    private final Boolean isGraded;
    private final String writerName;
    private final Long challengeId;
    private final List<QuizFileDto> quizFiles;
}
