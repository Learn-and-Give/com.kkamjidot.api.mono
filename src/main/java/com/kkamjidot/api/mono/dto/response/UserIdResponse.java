package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "유저 ID 응답")
public class UserIdResponse {
    @Builder
    public UserIdResponse(Long userId) {
        this.userId = userId;
    }

    private final Long userId;
}
