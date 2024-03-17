package com.bookdodum.meetingservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private String userId;
    private String name;
    private String userCode;
}
