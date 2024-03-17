package com.bookdodum.meetingservice.exception;

import com.bookdodum.meetingservice.type.ErrorType;
import lombok.Getter;

@Getter
public class MeetingServiceException extends RuntimeException {
    private final ErrorType errorType;

    public MeetingServiceException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
