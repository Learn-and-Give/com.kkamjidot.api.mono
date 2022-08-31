package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.FileDto;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.dto.response.QuizResponse;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizFileRepository;
import com.kkamjidot.api.mono.repository.QuizRepository;
import com.kkamjidot.api.mono.repository.ReadableRepository;
import com.kkamjidot.api.mono.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final QuizRepository quizRepository;
    private final QuizFileRepository quizFileRepository;
    private final AwsS3Service awsS3Service;

    @Value("${spring.profiles.active}")
    String active;

    @Transactional
    public void createOne(Quiz quiz, List<MultipartFile> quizFiles) {
        quizRepository.save(quiz);

        // 파일 업로드
        if(quizFiles != null) {
            for (MultipartFile quizFile : quizFiles) {
                FileDto fileDto = awsS3Service.upload(quizFile, active + "/quiz");
                QuizFile quizFileSave = quizFileRepository.save(QuizFile.of(fileDto, quiz));
                LOGGER.info("file upload: {}", quizFileSave);
            }
        }
    }

    @Transactional
    public void updateOne(Long quizId, User user, UpdateQuizRequest request) {
        Quiz quiz = quizRepository.findByIdAndUserAndQuizDeletedDateNull(quizId, user).orElseThrow(() -> new UnauthorizedException("내 퀴즈가 아닙니다."));
        quiz.update(request);
    }
}
