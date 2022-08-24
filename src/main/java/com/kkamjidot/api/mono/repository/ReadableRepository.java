package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Readable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadableRepository extends JpaRepository<Readable, Long> {
}