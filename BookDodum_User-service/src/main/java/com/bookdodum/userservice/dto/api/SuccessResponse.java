package com.bookdodum.userservice.dto.api;

import com.bookdodum.userservice.type.SuccessType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class SuccessResponse {
    private int status;
    private String message;
    private Object data;

    private SuccessResponse(int status, String message, Object data) {
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

    public static SuccessResponse of(HttpStatus status, String message, Object data) {
        return SuccessResponse.builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }

    public static SuccessResponse of(SuccessType successType, Object data) {
        return SuccessResponse.builder()
                .status(successType.getStatusCode())
                .message(successType.getMessage())
                .data(data)
                .build();
    }
}
