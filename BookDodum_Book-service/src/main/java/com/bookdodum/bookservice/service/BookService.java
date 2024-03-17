package com.bookdodum.bookservice.service;

import com.bookdodum.bookservice.dto.api.ApiResponseDto;
import com.bookdodum.bookservice.dto.request.BookRequestDto;

public interface BookService {
    ApiResponseDto createMyBook(String userCode, Long bookId);
    ApiResponseDto detailMyBook(String userCode, Long bookId);
    ApiResponseDto listMyBook(String userCode);
    ApiResponseDto listReadWith(Long bookId);
    ApiResponseDto deleteMyBook(String userCode, Long bookId);
    ApiResponseDto updateMyBook(String userCode, Long bookId);
    ApiResponseDto listMyFinishedBook(String userCode);
    ApiResponseDto findBook(BookRequestDto requestDto);
}
