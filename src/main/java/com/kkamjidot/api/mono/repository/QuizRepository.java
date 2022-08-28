package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByIdAndQuizDeletedDateNull(Long id);
}