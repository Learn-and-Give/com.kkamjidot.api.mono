package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "퀴즈 정답 응답")
public record QuizAnswerResponse(StaticQuiz quiz,
                                 StaticSolve solve) {

    public QuizAnswerResponse(Quiz quiz, Solve solve) {
        this(new StaticQuiz(quiz), new StaticSolve(solve));
    }

    record StaticQuiz(Long quizId, String answer, String explanation, String rubric) {
        StaticQuiz(Quiz quiz) {
            this(quiz.getId(), quiz.getQuizAnswer(), quiz.getQuizExplanation(), quiz.getQuizRubric());
        }
    }

    record StaticSolve(String answer, Integer score, String rubric) {
        StaticSolve(Solve solve) {
            this(solve.getSolveAnswer(), solve.getSolveScore(), solve.getSolveRubric());
        }
    }
}
