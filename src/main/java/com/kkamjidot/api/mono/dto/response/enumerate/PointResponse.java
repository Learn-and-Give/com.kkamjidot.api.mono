package com.kkamjidot.api.mono.dto.response.enumerate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Schema(description = "포인트")
public record PointResponse(Long userId, Integer point) { }
