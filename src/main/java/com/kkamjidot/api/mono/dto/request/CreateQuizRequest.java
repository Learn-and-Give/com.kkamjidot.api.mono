package com.kkamjidot.api.mono.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(name = "퀴즈 제출 요청")
public class CreateQuizRequest implements Serializable {
    @Schema(description = "퀴즈 제목(퀴즈 제목은 1자 50자 이하여야 합니다.)", example = "퀴즈 제목", required = true)
    @Size(min = 1, max = 50, message = "퀴즈 제목은 1자 이상 50자 이하여야 합니다.")
    private String quizTitle;

    @Schema(description = "퀴즈 내용(문제는 1자 3500자 이하여야 합니다.)", example = "문제 내용", required = true)
    @Size(min = 1, max = 3500, message = "문제는 1자 이상 3500자 이하여야 합니다.")
    private String quizContent;

    @Schema(description = "모범 답안(모범 답안은 1자 3500자 이하여야 합니다.)", example = "모범 답안", required = true)
    @Size(min = 1, max = 3500, message = "모범 답안은 1자 이상 3500자 이하여야 합니다.")
    private String quizAnswer;

    @Schema(description = "해설(해설은 1자 3500자 이하여야 합니다.)", example = "해설", required = true)
    @Size(min = 1, max = 3500, message = "해설은 1자 이상 3500자 이하여야 합니다.")
    private String quizExplanation;

    @Schema(description = "루브릭(루브릭은 1자 3500자 이하여야 합니다.)", example = "루브릭", required = true)
    @Size(min = 1, max = 3500, message = "루브릭은 1자 이상 3500자 이하여야 합니다.")
    private String quizRubric;

    @Schema(description = "출처(출처는 1자 3500자 이하여야 합니다.)", example = "출처", required = true)
    @Size(min = 1, max = 3500, message = "출처는 1자 이상 1000자 이하여야 합니다.")
    private String quizSource;

    @Schema(required = false)
    private MultipartFile[] quizFiles;
}
