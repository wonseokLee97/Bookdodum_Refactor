package com.bookdodum.bookservice.controller;


import com.bookdodum.bookservice.dto.api.ApiResponseDto;
import com.bookdodum.bookservice.dto.request.BookRequestDto;
import com.bookdodum.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ApiResponseDto<?> findBook(@RequestBody BookRequestDto requestDto) {
        return bookService.findBook(requestDto);
    }

    // 읽는 도서 등록
    @PostMapping("{usercode}/{bookid}")
    public ApiResponseDto<?> createMyBook(@PathVariable("usercode") String userCode,
                                          @PathVariable("bookid") Long bookId) {
        return bookService.createMyBook(userCode, bookId);
    }


    // 내 도서 상세조회
    @GetMapping("{usercode}/{bookid}")
    public ApiResponseDto<?> detailMyBook(@PathVariable("usercode") String userCode,
                                          @PathVariable("bookid") Long bookId) {
        return bookService.detailMyBook(userCode, bookId);
    }


    // 내 도서 전체 목록 조회
    @GetMapping("{usercode}")
    public ApiResponseDto<?> listMyBook(@PathVariable("usercode") String userCode) {
        return bookService.listMyBook(userCode);
    }


    // 독서를 마친 내 도서 목록 조회
    @GetMapping("{usercode}/finished")
    public ApiResponseDto<?> listMyFinishedBook(@PathVariable("usercode") String userCode) {
        return bookService.listMyFinishedBook(userCode);
    }



    // 도서를 읽고 있는 사람의 목록 조회
    @GetMapping("{bookid}/readwith")
    public ApiResponseDto<?> listReadWith(@PathVariable("bookid") Long bookId) {
        return bookService.listReadWith(bookId);
    }


    // 등록 도서 삭제
    @DeleteMapping("{usercode}/{bookid}")
    public ApiResponseDto<?> deleteMyBook(@PathVariable("usercode") String userCode,
                                          @PathVariable("bookid") Long bookId) {
        return bookService.deleteMyBook(userCode, bookId);
    }


    // 다 읽은 도서 갱신 (endDate 갱신)
    @PutMapping("{usercode}/{bookid}")
    public ApiResponseDto<?> updateMyBook(@PathVariable("usercode") String userCode,
                                          @PathVariable("bookid") Long bookId) {
        return bookService.updateMyBook(userCode, bookId);
    }


    // ISBN 조회
}
