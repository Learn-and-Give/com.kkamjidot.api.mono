package com.kkamjidot.api.mono.commons.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Auth 어노테이션
 * - Controller에서 @Auth 어노테이션이 붙은 메소드는 AuthInterceptor에서 인증 여부 체크를 한다.
 *
 * @author kim-seunggyu
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface NoAuth {
}
