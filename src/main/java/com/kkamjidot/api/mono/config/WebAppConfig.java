package com.kkamjidot.api.mono.config;

import com.kkamjidot.api.mono.commons.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringBoot Application 설정
 *
 * @author kim-seunggyu
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    public WebAppConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    /**
     * CORS 허가 설정
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        long MAX_AGE_SECS = 3600;
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://kkamjidot.com", "https://www.kkamjidot.com", "https://dev.kkamjidot.com", "https://test.kkamjidot.com")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

    /**
     * Interceptor 추가
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/v1/**", "/v2/**");
//                .excludePathPatterns("/swagger-ui/index.html", "/swagger-ui.html");
        //.excludePathPatterns("/favicon.ico", "/js/**", "/css/**", "/img/**", "/fonts/**");
    }
}
