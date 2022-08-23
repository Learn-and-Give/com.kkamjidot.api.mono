package com.kkamjidot.api.mono.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QuizFileDto implements Serializable {
    private final Long id;
    private final String qfName;
    private final String qfType;
    private final String qfPath;
    private final LocalDateTime qfCreatedDate;
    private final LocalDateTime qfModifiedDate;
}
