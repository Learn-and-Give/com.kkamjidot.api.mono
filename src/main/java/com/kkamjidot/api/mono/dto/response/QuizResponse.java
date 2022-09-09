package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
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
@Setter
@Builder
@Schema(name = "퀴즈 내용 응답")
public class QuizResponse implements Serializable {
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
    private final Boolean isMine;
    private final String solveAnswer;
    private final Integer solveScore;
    private final String writerName;
    private final Integer cntOfGood;
    private final Long challengeId;
    private final List<QuizFileDto> quizFiles;

    public void addQuizFiles(QuizFileDto quizFile) {
        this.quizFiles.add(quizFile);
    }

    public static QuizResponse of(Quiz quiz, User user, Solve solve, Integer cntOfGood) {
        QuizResponse quizResponse = QuizResponse.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getQuizTitle())
                .quizWeek(quiz.getQuizWeek())
                .quizCategory(quiz.getQuizCategory())
                .quizContent(quiz.getQuizContent())
                .quizAnswer(solve.getSolveAnswer() != null ? quiz.getQuizAnswer() : null)
                .quizExplanation(solve.getSolveAnswer() != null ? quiz.getQuizExplanation() : null)
                .quizRubric(solve.getSolveAnswer() != null ? quiz.getQuizRubric() : null)
                .quizSource(solve.getSolveAnswer() != null ? quiz.getQuizSource() : null)
                .quizCreatedDate(quiz.getQuizCreatedDate())
                .quizModifiedDate(quiz.getQuizModifiedDate())
                .isMine(quiz.isMine(user))
                .solveAnswer(solve.getSolveAnswer())
                .solveScore(solve.getSolveScore())
                .writerName(quiz.getWriterName())
                .cntOfGood(cntOfGood)
                .challengeId(quiz.getChallengeId())
                .quizFiles(new ArrayList<QuizFileDto>())
                .build();

        // 파일 추가
        Set<QuizFile> quizFilesSet = quiz.getQuizFiles();
        quizFilesSet.forEach(qf -> quizResponse.addQuizFiles(QuizFileDto.of(qf)));

        return quizResponse;
    }
}
