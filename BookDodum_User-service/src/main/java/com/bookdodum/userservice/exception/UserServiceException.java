package com.bookdodum.userservice.exception;

import com.bookdodum.userservice.type.ErrorType;
import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {
    private final ErrorType errorType;

    public UserServiceException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
