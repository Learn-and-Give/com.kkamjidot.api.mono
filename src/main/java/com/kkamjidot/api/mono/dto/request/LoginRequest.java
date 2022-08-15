package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Schema(name = "로그인 요청")
public class LoginRequest implements Serializable {
    @Schema(description = "이름", example = "홍길동", required = true)
    @NotNull(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "비밀번호", example = "1234", required = true)
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    private String code;
}
