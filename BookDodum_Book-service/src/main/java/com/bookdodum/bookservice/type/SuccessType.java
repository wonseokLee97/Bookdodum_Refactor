package com.bookdodum.bookservice.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessType {

    // 200: 요청 성공
    SUCCESS_CREATE_MY_BOOK(200, "유저 도서 등록 성공."),
    SUCCESS_DETAIL_MY_BOOK(200,"도서 상세정보 조회 성공."),
    SUCCESS_NO_USERS_READING(200, "도서를 읽고 있는 유저가 없습니다."),
    SUCCESS_NO_FINISH_MY_BOOK(200, "읽기를 완료한 도서가 없습니다"),
    SUCCESS_LIST_MY_FINISHED_BOOK(200, "읽기를 완료한 도서가 없습니다"),
    SUCCESS_LIST_READ_WITH(200, "도서를 함께 읽고 있는 유저 조회 성공"),
    SUCCESS_LIST_MY_BOOK(200, "유저 도서 전체 목록 조회 성공."),
    SUCCESS_DELETE_MY_BOOK(200, "유저 도서 삭제 성공"),
    SUCCESS_UPDATE_MY_BOOK(200, "유저 도서 업데이트 성공"),
    SUCCESS_LIST_BOOK(200, "도서 조회 성공."),
    SUCCESS_NO_LIST_BOOK(200, "해당 도서를 찾을 수 없습니다.");

    private final int statusCode;
    private final String message;
}
