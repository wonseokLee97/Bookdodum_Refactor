package com.bookdodum.bookservice;


import com.bookdodum.bookservice.dto.api.ApiResponseDto;
import com.bookdodum.bookservice.dto.api.ErrorResponse;
import com.bookdodum.bookservice.dto.api.ResponseUtils;
import com.bookdodum.bookservice.exception.BookServiceUncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BookServiceUncheckedException.class)
    public ResponseEntity<ApiResponseDto<ErrorResponse>> handleBookServiceException(BookServiceUncheckedException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorType());
        log.error(response.getMessage());
        return ResponseEntity
                .status(response.getStatus())
                .body(ResponseUtils.error(response));
    }
}
