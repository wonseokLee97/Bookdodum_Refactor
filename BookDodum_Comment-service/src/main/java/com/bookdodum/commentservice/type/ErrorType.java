package com.bookdodum.commentservice.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {

    // 400 BAD_REQUEST: 잘못된 요청
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
    INVALID_TYPE(400, "잘못된 응답 타입 입니다."),

    // 403 FORBIDDEN: 권한 없음
    INVALID_AUTHORIZATION(403, "권한이 유효하지 않습니다."),

    // 404 NOT_FOUND: 잘못된 리소스 접근
    NOT_FOUND_COMMENT(404, "존재하지 않는 댓글 입니다."),
    NOT_FOUND_MEETING(404, "존재하지 않는 모임 입니다."),

    // 409 CONFLICT: 요청이 서버의 현재 상태와 충돌
    ALREADY_DELETED_COMMENT(409, "이미 삭제되거나 존재하지 않는 댓글 입니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 팀에 연락주세요!");

    private final int statusCode;
    private final String message;
}
