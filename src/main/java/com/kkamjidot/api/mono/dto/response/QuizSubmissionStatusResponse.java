package com.kkamjidot.api.mono.dto.response;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Complete;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.QuizSubmissionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@Schema(name = "퀴즈 제출 현황 응답")
public class QuizSubmissionStatusResponse implements Serializable {
    @Schema(description = "챌린지 ID") private Long challengeId;
    @Schema(description = "챌린지가 진행되는 주차 수") private Integer totalWeeks;
    @Schema(description = "유저별 퀴즈 제출 현황 리스트") private List<QuizSubmissionStatusByUser> quizSubmissionStatusByUser;

    @Data
    @Builder
    @Schema(description = "유저별 퀴즈 제출 현황")
    static class QuizSubmissionStatusByUser implements Serializable {
        @Schema(description = "유저 ID") private Long userId;
        @Schema(description = "유저 이름") private String userName;
        @Schema(description = "주차별 퀴즈 제출 수 리스트") private List<NumberOfQuizzesSubmittedByWeek> numberOfQuizzesSubmittedByWeek;
    }

    @Data
    @Builder
    @Schema(description = "주차별 퀴즈 제출 수")
    static class NumberOfQuizzesSubmittedByWeek implements Serializable {
        @Schema(description = "주차") private Integer week;
        @Schema(description = "퀴즈 제출 수") private Integer numberOfQuizzesSubmitted;
        @Schema(description = "미션 완료 여부") private Boolean isCompleted;
        @Schema(description = "왕관 여부") private Boolean isTakenCrown;
    }

    static QuizSubmissionStatusResponse of(Challenge challenge, List<User> challengers, List<QuizSubmissionDto> quizSubmissionDtoList, List<Complete> completes) {
        List<QuizSubmissionStatusByUser> quizSubmissionStatusByUser = new ArrayList<>();

        // 주차별 최고 퀴즈 제출 수
        int[] maxNumberOfQuizzesSubmittedByWeek = new int[challenge.getChallTotalWeeks()];
        for (QuizSubmissionDto quizSubmissionDto : quizSubmissionDtoList) {
            if (maxNumberOfQuizzesSubmittedByWeek[quizSubmissionDto.getWeek() - 1] < quizSubmissionDto.getNumberOfQuizzesSubmitted()) {
                maxNumberOfQuizzesSubmittedByWeek[quizSubmissionDto.getWeek() - 1] = quizSubmissionDto.getNumberOfQuizzesSubmitted();
            }
        }

        // 응답 객체 생성
        for (User user : challengers) {
            List<NumberOfQuizzesSubmittedByWeek> numberOfQuizzesSubmittedByWeek = new ArrayList<>();
            quizSubmissionDtoList.stream()
                    .filter(quizSubmissionDto -> Objects.equals(quizSubmissionDto.getUserId(), user.getId()))
                    .forEach(quizSubmissionDto -> {
                        numberOfQuizzesSubmittedByWeek.add(NumberOfQuizzesSubmittedByWeek.builder()
                                .week(quizSubmissionDto.getWeek())
                                .numberOfQuizzesSubmitted(quizSubmissionDto.getNumberOfQuizzesSubmitted())
                                .isCompleted(completes.stream().anyMatch(complete -> Objects.equals(complete.getUser().getId(), user.getId())
                                                                                     && Objects.equals(complete.getWeek(), quizSubmissionDto.getWeek())))
                                .isTakenCrown(quizSubmissionDto.getNumberOfQuizzesSubmitted() > challenge.getChallMinNumOfQuizzesByWeek()
                                              && maxNumberOfQuizzesSubmittedByWeek[quizSubmissionDto.getWeek() - 1] == quizSubmissionDto.getNumberOfQuizzesSubmitted())
                                .build());

                    });

            quizSubmissionStatusByUser.add(QuizSubmissionStatusByUser.builder()
                    .userId(user.getId())
                    .userName(user.getUserName())
                    .numberOfQuizzesSubmittedByWeek(numberOfQuizzesSubmittedByWeek)
                    .build());
        }

        return QuizSubmissionStatusResponse.builder()
                .challengeId(challenge.getId())
                .totalWeeks(challenge.getChallTotalWeeks())
                .quizSubmissionStatusByUser(quizSubmissionStatusByUser)
                .build();
    }
}
/*
{
    "challengeId": 1,
    "totalWeeks": 14,
    "quizSubmissionStatusByUser": [
        {
            "userId": 1,
            "userName": "John Doe",
            "numberOfQuizzesSubmittedByWeek": [
                {
                    "week": 1,
                    "numberOfQuizzesSubmitted": 3,
                    "isCompleted": true,
                    "isTakenCrown": true
                },
                {
                    "week": 2,
                    "numberOfQuizzesSubmitted": 0,
                    "isCompleted": false,
                    "isTakenCrown": false
                }
            ]
        }
    ]
}
 */