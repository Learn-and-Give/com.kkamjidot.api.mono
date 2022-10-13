package com.kkamjidot.api.mono.commons.utility;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class JwtTokenDto {
    private Long userId;
//    // Check for Expiration time
//    @Builder.Default private Boolean willExpire = false;
}
