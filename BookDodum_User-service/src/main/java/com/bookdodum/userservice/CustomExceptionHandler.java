package com.bookdodum.userservice;

import com.bookdodum.userservice.dto.api.ApiResponseDto;
import com.bookdodum.userservice.dto.api.ErrorResponse;
import com.bookdodum.userservice.dto.api.ResponseUtils;
import com.bookdodum.userservice.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ApiResponseDto<ErrorResponse> handleMeetingServiceException(UserServiceException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorType());
        log.error(response.getMessage());
        return ResponseUtils.error(response);
    }
}
