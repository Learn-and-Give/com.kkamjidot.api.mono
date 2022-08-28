package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.dto.FileDto;
import com.kkamjidot.api.mono.repository.QuizFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizFileService {
    private final QuizFileRepository quizFileRepository;

    @Transactional
    public QuizFile createOne(FileDto fileDto, Quiz quiz) {
        QuizFile quizFile = QuizFile.of(fileDto, quiz);
        return quizFileRepository.save(quizFile);
    }
}
