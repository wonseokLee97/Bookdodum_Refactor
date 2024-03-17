package com.bookdodum.commentservice.service;


import com.bookdodum.commentservice.client.MeetingServiceClient;
import com.bookdodum.commentservice.dto.api.ApiResponseDto;
import com.bookdodum.commentservice.dto.api.ResponseUtils;
import com.bookdodum.commentservice.dto.api.SuccessResponse;
import com.bookdodum.commentservice.dto.request.CommentRequestDto;
import com.bookdodum.commentservice.dto.response.CommentResponseDto;
import com.bookdodum.commentservice.entity.Comment;
import com.bookdodum.commentservice.exception.CommentServiceException;
import com.bookdodum.commentservice.mq.CommentProducer;
import com.bookdodum.commentservice.repository.CommentRepository;
import com.bookdodum.commentservice.type.ErrorType;
import com.bookdodum.commentservice.type.SuccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentProducer commentProducer;
    private final MeetingServiceClient meetingServiceClient;


    @Override
    public ApiResponseDto createComment(CommentRequestDto commentDto, String userCode, Long meetingId) {
//        commentRepository.save(commentDto.toEntity(userCode, meetingId));

        // 댓글을 작성하려는 모임이 존재해야 한다.
        ApiResponseDto<?> responseDto = meetingServiceClient.getMeeting(meetingId);

        if (!responseDto.isSuccess()) {
            throw new CommentServiceException(ErrorType.NOT_FOUND_MEETING);
        }


        CommentResponseDto dto = CommentResponseDto.builder()
                .content(commentDto.getContent())
                .meetingId(meetingId)
                .userCode(userCode)
                .build();

        commentProducer.send("comment", dto);

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_CREATE_COMMENT,
                null));
    }

    @Override
    public ApiResponseDto updateComment(CommentRequestDto commentDto, String userCode, Long commentId) {
        // 수정하려는 댓글이 존재해야 한다.
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentServiceException(ErrorType.NOT_FOUND_COMMENT)
        );

        // 댓글 수정은 작성자만 할 수 있다.
        if (!comment.getUserCode().equals(userCode)) {
            throw new CommentServiceException(ErrorType.INVALID_AUTHORIZATION);
        }

        comment.update(commentDto);

        return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_UPDATE_COMMENT));
    }

    @Override
    public ApiResponseDto deleteComment(String userCode, Long commentId) {
        // 삭제하려는 댓글이 존재해야 한다.
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentServiceException(ErrorType.NOT_FOUND_COMMENT)
        );

        // 댓글 삭제는 작성자만 할 수 있다.
        if (!comment.getUserCode().equals(userCode)) {
            throw new CommentServiceException(ErrorType.INVALID_AUTHORIZATION);
        }

        long deleteCnt = commentRepository.deleteByIdAndUserCode(commentId, userCode);

        // 삭제가 제대로 이루어지지 않았다면 예외처리.
        if (deleteCnt == 0) {
            throw new CommentServiceException(ErrorType.ALREADY_DELETED_COMMENT);
        }

        return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_DELETE_COMMENT));
    }

    @Override
    public ApiResponseDto deleteAllMeetingComment(Long meetingId) {
        long commentCnt = commentRepository.findAllByMeetingId(meetingId);

        if (commentCnt == 0) {
            return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_NO_DELETE_COMMENT));
        }

        // 유저 코드와 미팅 id가 일치하는 모든 댓글을 삭제한다.
        long deleteCnt = commentRepository.deleteAllByMeetingId(meetingId);

        // 삭제가 제대로 이루어지지 않았다면 예외처리.
        if (deleteCnt == 0) {
            throw new CommentServiceException(ErrorType.ALREADY_DELETED_COMMENT);
        }

        return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_DELETE_COMMENT));
    }
}
