package com.bookdodum.bookservice.exception;

import com.bookdodum.bookservice.type.ErrorType;
import lombok.Getter;

@Getter
public class BookServiceCheckedException extends Exception {
    private final ErrorType errorType;

    public BookServiceCheckedException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public BookServiceCheckedException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public BookServiceCheckedException(Throwable cause, ErrorType errorType) {
        super(cause);
        this.errorType = errorType;
    }
}
