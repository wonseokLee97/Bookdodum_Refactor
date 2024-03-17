package com.bookdodum.commentservice.controller;


import com.bookdodum.commentservice.dto.api.ApiResponseDto;
import com.bookdodum.commentservice.dto.request.CommentRequestDto;
import com.bookdodum.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;


    // 댓글 등록
    @PostMapping("{usercode}/{meetingid}")
    public ApiResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                        @PathVariable("usercode") String userCode,
                                        @PathVariable("meetingid") Long meetingId) {
        ApiResponseDto comment = commentService.createComment(commentRequestDto, userCode, meetingId);

//        log.info(comment.getSuccessResponse().getMessage());
//        log.info(comment.getSuccessResponse().getStatus() + " !");
        return comment;
    }

    // 댓글 수정
    @PutMapping("{usercode}/{commentid}")
    public ApiResponseDto updateComment(@RequestBody CommentRequestDto commentRequestDto,
                                        @PathVariable("usercode") String userCode,
                                        @PathVariable("commentid") Long commentId) {
        return commentService.updateComment(commentRequestDto, userCode, commentId);
    }

    // 댓글 삭제
    @DeleteMapping("{usercode}/{commentid}")
    public ApiResponseDto deleteComment(@PathVariable("usercode") String userCode,
                                        @PathVariable("commentid") Long commentId) {
        return commentService.deleteComment(userCode, commentId);
    }

    // 모임과 관련된 댓글 모두 삭제
    @DeleteMapping("{meetingid}")
    public ApiResponseDto deleteAllMeetingComment(@PathVariable("meetingid") Long meetingId) {
        return commentService.deleteAllMeetingComment(meetingId);
    }
}
