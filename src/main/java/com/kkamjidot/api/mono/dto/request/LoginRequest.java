package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@Schema(name = "로그인 요청")
public class LoginRequest implements Serializable {
    @Schema(description = "이메일", example = "kkamjidot@gmail.com", required = true)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])[\40-\176]{8,255}$", message = "비밀번호는 영어, 숫자, 특수문자로 이루어지고 8자 이상 255자 이하여야 합니다.")
    @Schema(description = "비밀번호", example = "kkamjidot123", required = true)
    private String password;

    @Schema(description = "로그인하는 플랫폼", example = "MacIntel", required = true) private String platform;

    @Schema(description = "fcm 토큰", required = false) private String fcmToken;
}
