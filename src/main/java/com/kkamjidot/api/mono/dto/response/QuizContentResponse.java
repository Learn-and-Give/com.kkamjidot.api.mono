package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import com.kkamjidot.api.mono.dto.QuizFileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@Builder
@Schema(description = "퀴즈 내용 응답")
public class QuizContentResponse {
    private final Long challengeId;
    private final Long quizId;
    private final String quizTitle;
    private final Integer quizWeek;
    private final String quizContent;
    private final LocalDateTime quizCreatedDate;
    private final LocalDateTime quizModifiedDate;
    private final String writerName;
    private final List<QuizFileDto> quizFiles;
    @Schema(description = "좋아요 수") private final Integer cntOfGood;
    @Schema(description = "문제 풀린 횟수") private final Integer cntOfSolved;
    private final QuizInfoByUser quizInfoByUser;

    public void addQuizFiles(QuizFileDto quizFile) {
        this.quizFiles.add(quizFile);
    }

    public static QuizContentResponse of(Quiz quiz, User user, Solve solve, Integer cntOfGood, Integer cntOfSolved, RateValue didIRate) {
        QuizContentResponse quizResponse = QuizContentResponse.builder()
                .challengeId(quiz.getChallengeId())
                .quizId(quiz.getId())
                .quizTitle(quiz.getQuizTitle())
                .quizWeek(quiz.getQuizWeek())
                .quizContent(quiz.getQuizContent())
                .quizCreatedDate(quiz.getQuizCreatedDate())
                .quizModifiedDate(quiz.getQuizModifiedDate())
                .writerName(quiz.getWriterName())
                .cntOfGood(cntOfGood)
                .cntOfSolved(cntOfSolved)
                .quizInfoByUser(new QuizInfoByUser(quiz, user, solve, didIRate))
                .quizFiles(new ArrayList<QuizFileDto>())
                .build();

        // 파일 추가
        Set<QuizFile> quizFilesSet = quiz.getQuizFiles();
        quizFilesSet.forEach(qf -> quizResponse.addQuizFiles(QuizFileDto.of(qf)));

        return quizResponse;
    }

    record QuizInfoByUser(@Schema(description = "유저 ID") Long userId,
                          @Schema(description = "내가 작성한 문제인지 여부") Boolean isMine,
                          @Schema(description = "내가 한 평가 내용") RateValue didIRate,
                          @Schema(description = "제출한 정답") String solveAnswer) {

        QuizInfoByUser(Quiz quiz, User user, Solve solve, RateValue didIRate) {
            this(user.getId(), quiz.isMine(user), didIRate, solve.getSolveAnswer());
        }
    }
}
