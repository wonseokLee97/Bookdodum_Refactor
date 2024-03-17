package com.bookdodum.commentservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MeetingResponseDto {

    private Long id;
    private String title;
    private String content;
    private boolean authority;
    private String userName;
    private String userCode;
    private String bookCode;
    private Date createTime;
}