package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@Schema(description = "토큰 등록 요청")
public class RegisterFcmTokenRequest implements Serializable {
    @Schema(description = "로그인하는 플랫폼", example = "MacIntel", required = true) private String platform;

    @Schema(description = "fcm 토큰", required = true) private String fcmToken;
}