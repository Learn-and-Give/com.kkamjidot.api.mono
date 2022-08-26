package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.TakeAClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public List<Challenge> findAll() {
        return challengeRepository.findByChallDeletedDateNull();
    }

    public Challenge findOne(Long challengeId) throws NoSuchElementException {
        return challengeRepository.findByIdAndChallDeletedDateNull(challengeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
    }
}