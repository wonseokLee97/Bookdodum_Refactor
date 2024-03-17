package com.bookdodum.meetingservice.dto.api;

public class ResponseUtils {

    public static <T> ApiResponseDto<T> ok(T response) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .response(response)
                .build();
    }

    public static <T> ApiResponseDto<T> error(T response) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .response(response)
                .build();
    }
}
