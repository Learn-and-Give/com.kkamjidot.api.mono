package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.TakeAClass;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
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
public class TakeAClassService {
    private final TakeAClassRepository takeAClassRepository;

    public Optional<ApplicationStatus> findApplicationStatus(Challenge challenge, User user) throws NoSuchElementException {
        return takeAClassRepository.findByChallAndUser(challenge, user).map(TakeAClass::getTcApplicationstatus);
    }

    public List<TakeAClass> findAllByUser(User user) {
        return takeAClassRepository.findByUser(user);
    }
}
