//package com.bookdodum.commentservice.repository;
//
//import com.bookdodum.commentservice.dto.api.ApiResponseDto;
//import com.bookdodum.commentservice.dto.request.CommentRequestDto;
//import com.bookdodum.commentservice.service.CommentService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@Import({CommentRepository.class})
//class CommentRepositoryTest {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private CommentService commentService;
//
//    @Test
//    void createComment() {
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
//        assertEquals(responseDto.getSuccessResponse().getStatus(), 200);
//    }
//}