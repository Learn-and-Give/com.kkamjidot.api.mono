package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public Challenge findById(Long challengeId) {
        return challengeRepository.findByIdAndChallDeletedDateNull(challengeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
    }
}
