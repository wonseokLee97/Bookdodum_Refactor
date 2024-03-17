package com.bookdodum.meetingservice.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessType {

    // 200: 요청 성공
    SUCCESS_INQUIRY_ALL_MEETING(200, "전체 도서모임 조회 성공."),
    SUCCESS_INQUIRY_MY_MEETING(200, "내 도서모임 조회 성공."),
    SUCCESS_INQUIRY_MEETING(200, "도서모임 조회 성공."),
    SUCCESS_JOIN_MEETING(200, "도서모임 참여 성공."),
    SUCCESS_DELETE_MEETING(200, "도서모임 삭제 성공."),
    SUCCESS_CREATE_MEETING(200, "도서모임 생성 성공.");

    private final int statusCode;
    private final String message;
}
