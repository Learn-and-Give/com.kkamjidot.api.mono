package com.kkamjidot.api.mono.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Builder
@Schema(name = "로그인 응답")
public class LoginResponse implements Serializable {
    @Schema(description = "회원 ID") private final Long userId;
    @Schema(description = "토큰") private final String token;
}
