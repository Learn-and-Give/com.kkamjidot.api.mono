package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.exception.UserNotFoundException;
import com.kkamjidot.api.mono.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    public User login(String name, String code) throws UserNotFoundException {
        return userRepository.findByUserNameAndUserPasswordAndUserDeletedDateNull(name, code)
                .orElseThrow(() -> new UserNotFoundException("잘못된 이름 혹은 코드입니다."));
    }

    public User authorization(String code) throws UserNotFoundException {
        return userRepository.findByUserPassword(code)
                .orElseThrow(() -> new UserNotFoundException("잘못된 코드입니다."));
    }
}
