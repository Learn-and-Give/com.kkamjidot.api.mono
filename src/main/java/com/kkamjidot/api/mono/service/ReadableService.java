package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Readable;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReadableService {
    private final ReadableRepository readableRepository;

    public List<Integer> findReadableWeeksByUser(User user, Challenge challenge) {
        return readableRepository.findByUserAndChallAndIsReadableTrue(user, challenge).stream().map(Readable::getWeek).collect(Collectors.toList());
    }
}
