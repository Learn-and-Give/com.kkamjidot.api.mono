package com.kkamjidot.api.mono.commons.interceptor;

import com.kkamjidot.api.mono.commons.utility.JwtUtil;
import com.kkamjidot.api.mono.exception.UnauthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 로그인 여부를 체크하고 userId를 요청에 추가해주는 Interceptor
 *
 * @author kim-seunggyu
 * @see <a href="https://github1s.com/lifeandsong/SpringBootGradleTemplate/blob/HEAD/src/main/java/org/swmaestro/demo/config/WebAppConfig.java">멘토님 깃허브</a>
 * @see <a href="https://www.hides.kr/1093">Spring Interceptor를 활용하여 JWT인증 구현하기</a>
 * @see <a href="https://mslilsunshine.tistory.com/170">JWT을 활용한 로그인/ Interceptor를 활용한 인가 처리 구현</a>
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. Validation
        //  (1) Controller 내 Method(HandlerMethod)인가?
        if (!(handler instanceof HandlerMethod))
            return true;

        // (2) @Auth가 있는 method인가?
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
        if (noAuth != null) {
            log.debug("No auth interceptor");
            return true;
        }

        // (3) 여기에 Controller의 method 실행 전에 실행할 코드를 넣는다.
        //     - 예: 로그인 인증 처리 등

        String jwt = request.getHeader("Authorization");
        if (jwt == null) throw new UnauthenticatedException("Authorization 헤더가 없습니다.");
        if (!jwt.startsWith("Bearer ")) throw new UnauthenticatedException("Authorization 헤더의 형식이 올바르지 않습니다.");
        Long userId = jwtUtil.parseJWT(jwt.substring(7)).getUserId();

        // Controller 내 Method로 값을 보내려면, request.setAttribute()를 사용한다.
        request.setAttribute("userId", userId);

        // 4. 사용자 인증 성공
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
