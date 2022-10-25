package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.repository.PointRepository;
import com.kkamjidot.api.mono.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PointService {
    private final SolveRepository solveRepository;
    private final PointRepository pointRepository;

    public Long countOfAllQuizzesSolvedByUser(Long userId) {
        long point = solveRepository.countOfAllQuizzesSolvedByUser(userId) * 100L;
        point += orZero(pointRepository.totalGifticonPurchases(userId));
        return point;
    }

    public Integer findByUser(Long userId) {
        return pointRepository.findFirstByUserIdOrderByPoiDatetimeDesc(userId).getPoiBalance();
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }
}
