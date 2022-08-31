package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@Schema(name = "퀴즈 개요 응답")
public class QuizSummaryResponse implements Serializable {
    private final Long quizId;
    private final String quizTitle;
    private final Integer quizWeek;
    private final QuizCategory quizCategory;
    private final LocalDateTime quizCreatedDate;
    private final LocalDateTime quizModifiedDate;
    private final Boolean isMine;
    private final String solveAnswer;
    private final Integer solveScore;
    private final String writerName;
    private final Long challengeId;

    public static QuizSummaryResponse of(Quiz quiz, User user, Solve solve) {
        return QuizSummaryResponse.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getQuizTitle())
                .quizWeek(quiz.getQuizWeek())
                .quizCategory(quiz.getQuizCategory())
                .quizCreatedDate(quiz.getQuizCreatedDate())
                .quizModifiedDate(quiz.getQuizModifiedDate())
                .isMine(quiz.isMine(user))
                .solveAnswer(solve.getSolveAnswer())
                .solveScore(solve.getSolveScore())
                .writerName(quiz.getWriterName())
                .challengeId(quiz.getChallengeId())
                .build();
    }
}
