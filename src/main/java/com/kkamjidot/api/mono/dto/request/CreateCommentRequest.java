package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Schema(name = "댓글 등록 요청")
public class CreateCommentRequest implements Serializable {
    @Size(min = 1, max = 4000, message = "댓글 내용은 1자 이상 4000자 이하여야 합니다.")
    private String content;
}
