package com.kkamjidot.api.mono.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Profile({ "dev", "local" })        // "!prod"
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("깜지. API 서버")
                .version("v2.3.8")
                .description("학습자료 공유 기반 챌린지 서비스 플랫폼 깜지.의 API 통합 서버 (아래 README 사이트 참고)")
                .contact(new Contact().name("README").url("https://github.com/Learn-and-Give/com.kkamjidot.api.mono/tree/develop#readme"));

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
