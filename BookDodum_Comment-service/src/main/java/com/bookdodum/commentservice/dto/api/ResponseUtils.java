package com.bookdodum.commentservice.dto.api;

public class ResponseUtils {

    public static <T> ApiResponseDto<T> ok(T response) {
        return ApiResponseDto.<T>builder()
                .isSuccess(true)
                .response(response)
                .build();
    }

    public static <T> ApiResponseDto<T> error(T response) {
        return ApiResponseDto.<T>builder()
                .isSuccess(false)
                .response(response)
                .build();
    }
}
