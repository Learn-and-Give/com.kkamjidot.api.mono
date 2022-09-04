package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.QuizFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizFileRepository extends JpaRepository<QuizFile, Long> {
}