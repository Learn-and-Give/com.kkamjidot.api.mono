package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Readable;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadableRepository extends JpaRepository<Readable, Long> {
    List<Readable> findByUserAndChall(User user, Challenge chall);
    boolean existsByWeekAndUserAndChall(Integer week, User user, Challenge chall);
}