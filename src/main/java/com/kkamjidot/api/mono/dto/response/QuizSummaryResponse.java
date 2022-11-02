package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.QuizCategory;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
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
    @Schema(description = "정답 제출 여부") private final Boolean isSolved;
    @Schema(description = "문제 풀린 횟수") private final Integer cntOfSolved;
    private final String writerName;
    private final Long challengeId;
    private final Integer cntOfGood;
    private final RateValue didIRate;

    public static QuizSummaryResponse of(Quiz quiz, User user, Solve solve, Integer cntOfGood, Integer cntOfSolved, RateValue didIRate) {
        return QuizSummaryResponse.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getQuizTitle())
                .quizWeek(quiz.getQuizWeek())
                .quizCategory(quiz.getQuizCategory())
                .quizCreatedDate(quiz.getQuizCreatedDate())
                .quizModifiedDate(quiz.getQuizModifiedDate())
                .isMine(quiz.isMine(user))
                .isSolved(solve.isSolved())
                .writerName(quiz.getWriterName())
                .challengeId(quiz.getChallengeId())
                .cntOfGood(cntOfGood)
                .cntOfSolved(cntOfSolved)
                .didIRate(didIRate)
                .build();
    }
}
