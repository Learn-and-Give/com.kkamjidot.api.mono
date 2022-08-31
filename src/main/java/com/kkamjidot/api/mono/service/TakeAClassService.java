package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TakeAClassService {
    private final TakeAClassRepository takeAClassRepository;

    public Challenge authenticateProgress(Long challengeId, User user) throws NoSuchElementException {
        Challenge challenge = takeAClassRepository.findByTcApplicationstatusAndChall_IdAndUser(ApplicationStatus.ACCEPTED, challengeId, user)
                .orElseThrow(() -> new UnauthorizedException("수강중인 챌린지가 아닙니다.")).getChall();
        if (!challenge.isInProgress()) throw new UnauthorizedException("이미 종료된 챌린지입니다.");
        return challenge;
    }
}
