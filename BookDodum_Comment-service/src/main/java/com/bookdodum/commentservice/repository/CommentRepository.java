package com.bookdodum.commentservice.repository;

import com.bookdodum.commentservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    long deleteByIdAndUserCode(Long commentId, String userCode);
    long findAllByMeetingId(Long meetingId);
    long deleteAllByMeetingId(Long meetingId);
}
