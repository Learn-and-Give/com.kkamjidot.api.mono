package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.FileDto;
import com.kkamjidot.api.mono.dto.request.CreateQuizRequest;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.QuizFileRepository;
import com.kkamjidot.api.mono.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final UserService userService;
    private final CompleteService completeService;
    private final ChallengeService challengeService;

    @Transactional
    public Long createOne(CreateQuizRequest createQuizRequest, Long userId, Long challengeId, List<MultipartFile> quizFiles) {   //createQuizRequest, userId, challengeId, quizFiles
        User user = userService.findById(userId);
        Challenge challenge = challengeService.findById(challengeId);

        Quiz quiz = Quiz.of(createQuizRequest, user, challenge);

        // 퀴즈 제출
        quizRepository.save(quiz);

        // 파일 업로드
        if (quizFiles != null) {
            for (MultipartFile quizFile : quizFiles) {
                FileDto fileDto = awsS3Service.upload(quizFile, "quiz");
                QuizFile quizFileSave = quizFileRepository.save(QuizFile.of(fileDto, quiz));
                LOGGER.info("file upload: {}", quizFileSave);
            }
        }

        // 챌린지 퀴즈 제출 조건 이상의 퀴즈를 제출했으면 열람 가능한 권한 생성
        int count = countByWeek(challengeId, userId, quiz.getQuizWeek());
        if (challenge.isCountOfQuizzesIsEnough(count))
            completeService.createOneIfRight(challengeId, userId, quiz.getQuizWeek());

        return quiz.getId();
    }

    @Transactional
    public void updateAnswer(Long quizId, Long userId, UpdateQuizRequest request) {
        Quiz quiz = this.findOneMine(quizId, userId);
        quiz.update(request);
    }

    public Quiz findOneMine(Long quizId, Long userId) {
        return quizRepository.findByIdAndUserIdAndQuizDeletedDateNull(quizId, userId).orElseThrow(() -> new UnauthorizedException("내 퀴즈가 아니거나 존재하지 않는 퀴즈입니다."));
    }

    public int countByWeek(Long challengeId, Long userId, int week) {
        return quizRepository.countByChallenge_IdAndUserIdAndQuizWeekAndQuizDeletedDateNull(challengeId, userId, week);
    }

    public Quiz findById(Long quizId) {
        return quizRepository.findByIdAndQuizDeletedDateNull(quizId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 퀴즈입니다."));
    }
}
