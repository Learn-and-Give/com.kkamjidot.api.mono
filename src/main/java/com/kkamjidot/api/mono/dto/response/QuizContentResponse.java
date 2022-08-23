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
@Schema(name = "퀴즈 내용 응답")
public class QuizContentResponse implements Serializable {
    private final Long quizId;
    private final String quizTitle;
    private final Integer quizWeek;
    private final String quizCategory;
    private final String quizContent;
    private final LocalDateTime quizCreatedDate;
    private final LocalDateTime quizModifiedDate;
    private final Boolean isMine;
    private final String solveAnswer;
    private final Integer solveScore;
    private final String writerName;
    private final Integer week;
    private final Long challengeId;
    private final List<QuizFileDto> quizFiles;
}
