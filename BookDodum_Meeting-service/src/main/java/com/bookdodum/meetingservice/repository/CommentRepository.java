package com.bookdodum.meetingservice.repository;

import com.bookdodum.meetingservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long commentId);
}
