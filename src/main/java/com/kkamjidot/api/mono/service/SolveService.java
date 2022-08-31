package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SolveService {
    private final SolveRepository solveRepository;
}
