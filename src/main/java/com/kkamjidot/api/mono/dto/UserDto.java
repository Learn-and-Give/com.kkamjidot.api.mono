package com.kkamjidot.api.mono.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.kkamjidot.api.mono.domain.User} entity
 */
@Data
@Schema(description = "유저 Dto")
public class UserDto implements Serializable {
    @Schema(name = "유저 ID") private final Long id;
    @Schema(name = "유저 이름") private final String userName;
}