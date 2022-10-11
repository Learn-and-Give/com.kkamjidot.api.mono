package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.commons.utility.JwtTokenDto;
import com.kkamjidot.api.mono.commons.utility.JwtUtil;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UserNotFoundException;
import com.kkamjidot.api.mono.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
    private final UserRepository userRepository;
    public final JwtUtil jwtUtil;

    public String login(String email, String password, boolean isAutoLogin) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("잘못된 이메일 혹은 비밀번호입니다."));
        if (!user.getUserPassword().equals(password)) {
            throw new UserNotFoundException("잘못된 이메일 혹은 비밀번호입니다.");
        }

        return jwtUtil.createJWT(JwtTokenDto.builder().userId(user.getId()).build(), !isAutoLogin);
    }

//    public User authenticate(String jwt) throws UserNotFoundException {
//        return userRepository.findByUserPassword(code)
//                .orElseThrow(() -> new UserNotFoundException("잘못된 코드입니다."));
//    }
}
