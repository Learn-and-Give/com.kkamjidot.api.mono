package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@Schema(name = "로그인 요청")
public class LoginRequestV2 implements Serializable {
    @Schema(description = "이메일", example = "kkamjidot@gmail.com", required = true)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "kkamjidot123", required = true)
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 255자 이하여야 합니다.")
    private String password;

    @Schema(description = "로그인하는 플랫폼", example = "MacIntel", required = true) private String platform;

    @Schema(description = "토큰", required = false) private String fcmToken;
}
