package com.bookdodum.bookservice.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {

    // 400 BAD_REQUEST: 잘못된 요청
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
    INVALID_TYPE(400, "잘못된 응답 타입 입니다."),

    // 404 NOT_FOUND: 잘못된 리소스 접근
    NOT_FOUND_BOOK(404, "존재하지 않는 도서 입니다."),
    NOT_FOUND_USER_BOOK(404, "존재하지 않는 유저 도서 입니다."),
    NOT_FOUND_UPDATE_USER_BOOK(404, "미 완료된 유저 도서를 찾을 수 없습니다."),


    // 409 CONFLICT: 요청이 서버의 현재 상태와 충돌
    ALREADY_CREATED_MY_BOOK(409, "동일한 도서를 여러개 등록할 수 없습니다."),
    ALREADY_DELETED_MY_BOOK(409, "이미 삭제된 유저 도서 입니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 팀에 연락주세요!");

    private final int statusCode;
    private final String message;
}
