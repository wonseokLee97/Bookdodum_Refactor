//package com.bookdodum.commentservice.service;
//
//import com.bookdodum.commentservice.dto.api.ApiResponseDto;
//import com.bookdodum.commentservice.dto.request.CommentRequestDto;
//import com.bookdodum.commentservice.entity.Comment;
//import com.bookdodum.commentservice.repository.CommentRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class CommentServiceImplTest {
//
//    @InjectMocks
//    private CommentServiceImpl commentService;
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @Test
//    void createCommentSuccess() {
//        // given
//        CommentRequestDto commentDto = CommentRequestDto.builder()
//                .content("testComment")
//                .build();
//
//        String userCode = "testUserCode";
//        Long meetingId = 1L;
//
//
//        // when
//        ApiResponseDto responseDto = commentService.createComment(commentDto, userCode, meetingId);
//
//
//        // then
//        assertEquals(responseDto.getSuccessResponse().getStatus(), 200);
//        assertEquals(responseDto.getSuccessResponse().getMessage(), "댓글 등록 성공.");
//    }
//
//    @Test
//    void updateCommentSuccess() {
//        // given
//        String userCode = "testUserCode";
//        Long commentId = 1L;
//
//        Comment originalComment = Comment.builder()
//                .id(commentId)
//                .content("Original content")
//                .userCode(userCode)
//                .build();
//
//        CommentRequestDto commentUpdateDto = CommentRequestDto.builder()
//                .content("updateComment")
//                .build();
//
//        when(commentRepository.existsByIdAndUserCode(commentId, userCode)).thenReturn(true);
//        when(commentRepository.findByIdAndUserCode(commentId, userCode)).thenReturn(Optional.of(originalComment));
//
//
//        // when
//        ApiResponseDto responseDto = commentService.updateComment(commentUpdateDto, userCode, 1L);
//
//
//        // then
//        assertEquals(responseDto.getSuccessResponse().getStatus(), 200);
//        assertEquals(responseDto.getSuccessResponse().getMessage(), "댓글 수정 성공.");
//    }
//
//    @Test
//    void deleteComment() {
//        // given
//        CommentRequestDto commentDto = CommentRequestDto.builder()
//                .content("testComment")
//                .build();
//
//        String userCode = "testUserCode";
//        Long commentId = 1L;
//
//        Comment comment = Comment.builder()
//                .id(commentId)
//                .content("test comment")
//                .userCode(userCode)
//                .build();
//
//
//        commentService.createComment(commentDto, userCode, commentId);
//
//        when(commentRepository.existsByIdAndUserCode(commentId, userCode)).thenReturn(true);
//
//
//        // when
//        ApiResponseDto responseDto = commentService.deleteComment(userCode, 1L);
//
//
//        // then
//        assertEquals(responseDto.getSuccessResponse().getStatus(), 200);
//        assertEquals(responseDto.getSuccessResponse().getMessage(), "댓글 수정 성공.");
//    }
//
//    @Test
//    void deleteAllMeetingComment() {
//    }
//}