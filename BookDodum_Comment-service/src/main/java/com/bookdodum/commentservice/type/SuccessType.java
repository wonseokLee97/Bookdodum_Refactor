package com.bookdodum.commentservice.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessType {

    // 200: 요청 성공
    SUCCESS_CREATE_COMMENT(200, "댓글 등록 성공."),
    SUCCESS_UPDATE_COMMENT(200, "댓글 수정 성공."),
    SUCCESS_DELETE_COMMENT(200, "댓글 삭제 성공."),
    SUCCESS_NO_DELETE_COMMENT(200, "삭제할 댓글이 없습니다."),;

    private final int statusCode;
    private final String message;
}
