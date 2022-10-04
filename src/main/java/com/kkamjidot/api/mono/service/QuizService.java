package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.QuizFile;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.FileDto;
import com.kkamjidot.api.mono.dto.request.UpdateQuizRequest;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.CompleteRepository;
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
    private final CompleteRepository completeRepository;

    private final AwsS3Service awsS3Service;

    /**
     * 퀴즈를 열람 가능한지 권한을 확인한다. 특히, 퀴즈의 주차가 현재 주차보다 이전이고, 열람 가능한 권한이 있는지 검사한다.
     * */
    public Quiz findOneInReadableWeek(Long quizId, User user) throws RuntimeException {
        Quiz quiz = quizRepository.findByIdAndQuizDeletedDateNull(quizId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 퀴즈입니다."));

        // 퀴즈의 주차가 현재 주차보다 이전이고, 열람 가능한 권한이 있는지 검사
        if (quiz.getChallenge().getThisWeek() <= quiz.getQuizWeek() || !completeRepository.existsByWeekAndUserAndChall(quiz.getQuizWeek(), user, quiz.getChallenge()))
            throw new UnauthorizedException("열람 가능한 권한이 없습니다.");

        return quiz;
    }

    @Transactional
    public void createOne(Quiz quiz, List<MultipartFile> quizFiles) {
        quizRepository.save(quiz);

        // 파일 업로드
        if (quizFiles != null) {
            for (MultipartFile quizFile : quizFiles) {
                FileDto fileDto = awsS3Service.upload(quizFile, "quiz");
                QuizFile quizFileSave = quizFileRepository.save(QuizFile.of(fileDto, quiz));
                LOGGER.info("file upload: {}", quizFileSave);
            }
        }
    }

    @Transactional
    public void updateAnswer(Long quizId, User user, UpdateQuizRequest request) {
        Quiz quiz = this.findOneMine(quizId, user);
        quiz.update(request);
    }

    public Quiz findOneMine(Long quizId, User user) {
        return quizRepository.findByIdAndUserAndQuizDeletedDateNull(quizId, user).orElseThrow(() -> new UnauthorizedException("내 퀴즈가 아니거나 존재하지 않는 퀴즈입니다."));
    }

    public int countByWeek(Challenge challenge, User user, int week) {
        return quizRepository.countByChallengeAndUserAndQuizWeekAndQuizDeletedDateNull(challenge, user, week);
    }
}
