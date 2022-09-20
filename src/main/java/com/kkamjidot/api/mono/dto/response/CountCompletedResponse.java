package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "현재 주차 미션 성공 챌린지원 수")
public class CountCompletedResponse implements Serializable {
    int count;
}
