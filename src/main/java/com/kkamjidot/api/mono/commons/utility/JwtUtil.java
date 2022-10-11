package com.kkamjidot.api.mono.commons.utility;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidParameterException;
import java.security.Key;
import java.time.ZoneId;
import java.util.Date;


/**
 * JWT 토큰 생성 및 검증
 * 참고 사이트
 * - <a href="https://github.com/jwtk/jjwt">...</a>
 * - <a href="https://stormpath.com/blog/jwt-java-create-verify">...</a>
 * - <a href="https://ocblog.tistory.com/56">...</a>
 * - <a href="https://bibi6666667.tistory.com/311">...</a>
 * - <a href="https://lemontia.tistory.com/1021">...</a>
 */
@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.secret-key}")
    private String secretKey;

    // 30분
    private final long ACCESS_TOKEN_VALIDATiON_MILLISECOND = 60 * 30 * 1000L;

    // 1개월
    private final long REFRESH_TOKEN_VALIDATiON_MILLISECOND = 60 * 60 * 24 * 30 * 1000L;

    //토큰생성
    public String createJWT(JwtTokenDto tokenDto, boolean willExpire) {
        // 토큰에 서명하는 데 사용할 JWT 서명 알고리즘
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // JWT 서명에 ApiKey secret 사용
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims();
        claims.put("userId", tokenDto.getUserId());

        Date validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDATiON_MILLISECOND);

        return Jwts.builder()
                .setIssuer("kkamji")
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(willExpire ? validity : null)
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    private JwtTokenDto parseJWT(String jwt) {
        Claims claims;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build()
                    .parseClaimsJwt(jwt)
                    .getBody();

            return JwtTokenDto.builder()
                    .userId(claims.get("userId", Long.class))
                    .build();
        } catch (JwtException ex ) {
            log.error(ex.getMessage());
            throw new InvalidParameterException("유효하지 않은 토큰입니다");
        }
    }
}
