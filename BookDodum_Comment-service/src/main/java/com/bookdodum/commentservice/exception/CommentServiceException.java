package com.bookdodum.commentservice.exception;

import com.bookdodum.commentservice.type.ErrorType;
import lombok.Getter;

@Getter
public class CommentServiceException extends RuntimeException {
    private final ErrorType errorType;

    public CommentServiceException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
