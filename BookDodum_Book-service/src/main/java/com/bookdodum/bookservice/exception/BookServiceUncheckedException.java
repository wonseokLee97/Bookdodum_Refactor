package com.bookdodum.bookservice.exception;

import com.bookdodum.bookservice.type.ErrorType;
import lombok.Getter;

@Getter
public class BookServiceUncheckedException extends RuntimeException {
    private final ErrorType errorType;

    public BookServiceUncheckedException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public BookServiceUncheckedException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public BookServiceUncheckedException(Throwable cause, ErrorType errorType) {
        super(cause);
        this.errorType = errorType;
    }
}
