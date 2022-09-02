package com.kkamjidot.api.mono.service;

import com.kkamjidot.api.mono.domain.Comment;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.exception.UnauthorizedException;
import com.kkamjidot.api.mono.repository.ChallengeRepository;
import com.kkamjidot.api.mono.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void createOne(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteOne(Long commentId, User user) {
        commentRepository.findByIdAndUserAndCommentDeletedDateNull(commentId, user).orElseThrow(() -> new UnauthorizedException("존재하지 않는 댓글이거나 삭제할 수 있는 권한이 없습니다."));
        commentRepository.updateCommentDeletedDateById(LocalDateTime.now(ZoneId.of("Asia/Seoul")), commentId);
    }
}
