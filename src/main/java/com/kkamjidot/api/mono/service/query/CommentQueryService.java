package com.kkamjidot.api.mono.service.query;

import com.kkamjidot.api.mono.domain.Comment;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.kkamjidot.api.mono.dto.response.CommentResponse;
import com.kkamjidot.api.mono.repository.CommentRepository;
import com.kkamjidot.api.mono.service.CommentService;
import com.kkamjidot.api.mono.service.ReadableService;
import com.kkamjidot.api.mono.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentQueryService {
    private final CommentRepository commentRepository;

    public List<CommentResponse> readComments(User user, Quiz quiz) {
        List<Comment> comments = commentRepository.findByQuizAndCommentDeletedDateNullOrderByCommentCreatedDateAsc(quiz);
        return CommentResponse.listOf(comments, user);
    }
}
