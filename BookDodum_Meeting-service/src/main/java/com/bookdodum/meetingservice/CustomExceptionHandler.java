package com.bookdodum.meetingservice;


import com.bookdodum.meetingservice.dto.api.ApiResponseDto;
import com.bookdodum.meetingservice.dto.api.ErrorResponse;
import com.bookdodum.meetingservice.dto.api.ResponseUtils;
import com.bookdodum.meetingservice.exception.MeetingServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MeetingServiceException.class)
    public ResponseEntity<ApiResponseDto<ErrorResponse>> handleMeetingServiceException(MeetingServiceException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorType());
        log.error(response.getMessage());
        return ResponseEntity
                .status(response.getStatus())
                .body(ResponseUtils.error(response));
    }
}
