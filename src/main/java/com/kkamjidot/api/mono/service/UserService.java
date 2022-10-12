package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.request.UpdatePasswordRequest;
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

    public Long login(String name, String code) throws UserNotFoundException {
        return userRepository.findByUserNameAndUserPasswordAndUserDeletedDateNull(name, code)
                .orElseThrow(() -> new UserNotFoundException("잘못된 이름 혹은 코드입니다."))
                .getId();
    }

    public User authenticate_deprecated(String code) throws UserNotFoundException {
        return userRepository.findByUserPassword(code)
                .orElseThrow(() -> new UserNotFoundException("잘못된 코드입니다."));
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) throws UserNotFoundException, IllegalArgumentException {
        User user = userRepository.findByEmail(updatePasswordRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("잘못된 이메일 혹은 비밀번호입니다."));
        if (!user.isMatchPassword(updatePasswordRequest.getExistingPassword())) {
            throw new UserNotFoundException("잘못된 이메일 혹은 비밀번호입니다.");
        }

        validatePassword(updatePasswordRequest.getNewPassword());
        user.setPassword(updatePasswordRequest.getNewPassword());
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }
    }
}
