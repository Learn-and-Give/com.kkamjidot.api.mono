package com.kkamjidot.api.mono.commons.utility;

import com.kkamjidot.api.mono.exception.UserNotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidParameterException;
import java.security.Key;
import java.time.ZoneId;
import java.util.Date;


/**
 * JWT 토큰 생성 및 검증
 * 참고 사이트
 * - <a href="https://github.com/jwtk/jjwt">공식문서</a>
 * - <a href="https://stormpath.com/blog/jwt-java-create-verify">How to Create and verify JWTs in Java</a>
 * - <a href="https://ocblog.tistory.com/56">...</a>
 * - <a href="https://bibi6666667.tistory.com/311">...</a>
 * - <a href="https://lemontia.tistory.com/1021">[springboot, jwt] jwt 로 토큰 생성, 유효시간 관리 하기</a>
 * - <a href="https://yookeun.github.io/java/2020/12/29/spring-jwt/">Springboot에서 JWT 간단 사용하기</a> 차후 참고
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
    public String createJWT(JwtTokenDto tokenDto) {
        // 토큰에 서명하는 데 사용할 JWT 서명 알고리즘
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // JWT 서명에 ApiKey secret 사용
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        SecretKey signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

        Claims claims = Jwts.claims();
        claims.put("userId", tokenDto.getUserId());

        Date validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDATiON_MILLISECOND);

        String jwt = Jwts.builder()
                .setIssuer("kkamji")
                .setIssuedAt(now)
                .setClaims(claims)
//                .setExpiration(willExpire ? validity : null)
                .signWith(signingKey, signatureAlgorithm)
                .compact();
        return jwt;
    }

    public JwtTokenDto parseJWT(String jwt) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        SecretKey signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return JwtTokenDto.builder()
                    .userId(claims.get("userId", Long.class))
                    .build();
        } catch (SignatureException ex) {
            throw new UserNotFoundException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new InvalidParameterException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new InvalidParameterException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new InvalidParameterException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new InvalidParameterException("JWT claims string is empty.");
        }
    }
}
