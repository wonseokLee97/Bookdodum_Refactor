//package com.bookdodum.bookservice.controller;
//
//import com.bookdodum.bookservice.service.BookService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class BookControllerTest {
//
//    @InjectMocks
//    private BookController bookController;
//
//    // bookController 에 bookService 의존성 주입
//    @Mock
//    private BookService bookService;
//
//    // HTTP 호출을 위한 mockMvc 세팅
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
//    }
//
//    @Test
//    void createMyBookSuccess() throws Exception {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/{userCode}/{bookId}", userCode, bookId));
//
//        // then
//        resultActions.andExpect(status().isOk());
//    }
//}