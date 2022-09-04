package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.ChallengeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeInfoRepository extends JpaRepository<ChallengeInfo, Long> {
}