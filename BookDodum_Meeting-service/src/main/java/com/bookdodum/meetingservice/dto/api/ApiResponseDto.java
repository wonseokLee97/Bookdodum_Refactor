package com.bookdodum.meetingservice.dto.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDto<T> {
    private boolean success;

    // Error or Success Response
    private T response;

    public ApiResponseDto(boolean success, T response) {
        this.success = success;
        this.response = response;
    }
}