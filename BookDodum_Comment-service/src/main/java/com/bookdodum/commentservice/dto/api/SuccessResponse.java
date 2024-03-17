package com.bookdodum.commentservice.dto.api;

import com.bookdodum.commentservice.type.SuccessType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class SuccessResponse<T> {
    private int status;
    private String message;
    private T data;

    private SuccessResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static SuccessResponse of(SuccessType successType) {
        return SuccessResponse.builder()
                .status(successType.getStatusCode())
                .message(successType.getMessage())
                .build();
    }

    public static <T> SuccessResponse<T> of(HttpStatus status, String message, T data) {
        return SuccessResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> SuccessResponse<T> of(SuccessType successType, T data) {
        return SuccessResponse.<T>builder()
                .status(successType.getStatusCode())
                .message(successType.getMessage())
                .data(data)
                .build();
    }
}
