package com.bookdodum.userservice.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessType {

    // 200: 요청 성공
    SUCCESS_GET_USER(200, "유저 정보 조회 성공."),
    SUCCESS_SIGN_UP(200, "회원가입 성공."),
    SUCCESS_SIGN_IN(200, "로그인 성공");

    private final int statusCode;
    private final String message;
}
