package com.kkamjidot.api.mono.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Profile({ "dev", "local" })        // "!prod"
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
        Info info = new Info()
                .title("깜지. API 서버")
                .version(appVersion)
                .description("학습자료 공유 기반 챌린지 서비스 플랫폼 깜지.의 API 통합 서버 (아래 README 사이트 참고)")
                .contact(new Contact().name("README").url("https://github.com/Learn-and-Give/com.kkamjidot.api.mono/tree/develop#readme"));

        // SecuritySecheme명
        String jwtSchemeName = "jwt";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .in(SecurityScheme.In.HEADER) // 헤더에 인증정보 포함
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
//                .components(new Components())
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
