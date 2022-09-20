package com.kkamjidot.api.mono.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuizSubmissionDto implements Serializable {
    Integer week;
    Integer numberOfQuizzesSubmitted;
    Long userId;
    String userName;
}
