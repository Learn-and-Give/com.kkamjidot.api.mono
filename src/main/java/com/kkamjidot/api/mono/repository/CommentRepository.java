package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Comment;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserIdAndCommentDeletedDateNull(Long commentId, Long userId);

    @Transactional
    @Modifying
    @Query("update Comment c set c.commentDeletedDate = ?1 where c.id = ?2")
    void updateCommentDeletedDateById(LocalDateTime commentDeletedDate, Long id);

    List<Comment> findByQuizAndCommentDeletedDateNullOrderByCommentCreatedDateAsc(Quiz quiz);
}