package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.domain.enumerate.QuizCategory;
import com.kkamjidot.api.mono.dto.QuizFileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Builder
@Schema(name = "퀴즈 전체 내용 응답")
public class MyQuizResponse implements Serializable {
    private final Long quizId;
    private final String quizTitle;
    private final Integer quizWeek;
    private final QuizCategory quizCategory;
    private final String quizContent;
    private final String quizAnswer;
    private final String quizExplanation;
    private final String quizRubric;
    private final String quizSource;
    private final LocalDateTime quizCreatedDate;
    private final LocalDateTime quizModifiedDate;
    private final String writerName;
    private final Integer cntOfGood;
    private final Long challengeId;
    private List<QuizFileDto> quizFiles;

    public void addQuizFiles(QuizFileDto quizFile) {
        this.quizFiles.add(quizFile);
    }

    public static MyQuizResponse of(Quiz quiz, Integer cntOfGood) {
        MyQuizResponse response = MyQuizResponse.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getQuizTitle())
                .quizWeek(quiz.getQuizWeek())
                .quizCategory(quiz.getQuizCategory())
                .quizContent(quiz.getQuizContent())
                .quizAnswer(quiz.getQuizAnswer())
                .quizExplanation(quiz.getQuizExplanation())
                .quizRubric(quiz.getQuizRubric())
                .quizSource(quiz.getQuizSource())
                .quizCreatedDate(quiz.getQuizCreatedDate())
                .quizModifiedDate(quiz.getQuizModifiedDate())
                .writerName(quiz.getWriterName())
                .cntOfGood(cntOfGood)
                .challengeId(quiz.getChallengeId())
                .quizFiles(new ArrayList<QuizFileDto>())
                .build();

        // 파일 추가
        Set<QuizFile> quizFilesSet = quiz.getQuizFiles();
        quizFilesSet.forEach(qf -> response.addQuizFiles(QuizFileDto.of(qf)));

        return response;
    }
}
