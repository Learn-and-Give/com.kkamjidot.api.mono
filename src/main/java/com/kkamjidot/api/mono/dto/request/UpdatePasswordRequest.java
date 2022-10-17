package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Schema(name = "비밀번호 변경 요청")
public class UpdatePasswordRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "이메일", example = "kkamjidot@gmail.com", required = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Schema(description = "기존 비밀번호", example = "1234", required = true)
    private String existingPassword;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 255자 이하여야 합니다.")
    @Schema(description = "새로운 비밀번호", example = "kkamjidot123", required = true)
    private String newPassword;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 255자 이하여야 합니다.")
    @Schema(description = "새로운 비밀번호 확인", example = "kkamjidot123", required = true)
    private String newPasswordConfirm;
}
