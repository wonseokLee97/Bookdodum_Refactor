package com.bookdodum.commentservice.service;

import com.bookdodum.commentservice.dto.api.ApiResponseDto;
import com.bookdodum.commentservice.dto.request.CommentRequestDto;

public interface CommentService {

    ApiResponseDto createComment(CommentRequestDto commentRequestDto, String userCode, Long meetingId);

    ApiResponseDto updateComment(CommentRequestDto commentRequestDto, String userCode, Long commentId);

    ApiResponseDto deleteComment(String userCode, Long commentId);

    ApiResponseDto deleteAllMeetingComment(Long meetingid);
}
