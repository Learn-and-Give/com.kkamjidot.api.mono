package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Schema(name = "비밀번호 변경 요청")
public class UpdatePasswordRequest {
    @Schema(description = "이메일", example = "kkamjidot@gmail.com", required = true)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Schema(description = "기존 비밀번호", example = "1234", required = true)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String existingPassword;

    @Schema(description = "새로운 비밀번호", example = "", required = true)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String newPassword;
}
