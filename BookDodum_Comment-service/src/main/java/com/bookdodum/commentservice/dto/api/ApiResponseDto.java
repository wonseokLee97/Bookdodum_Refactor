package com.bookdodum.commentservice.dto.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDto<T> {
    private boolean isSuccess;

    // Error or Success Response
    private T response;

    public ApiResponseDto(boolean isSuccess, T response) {
        this.isSuccess = isSuccess;
        this.response = response;
    }
}