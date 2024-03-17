package com.bookdodum.meetingservice.dto.request;

import com.bookdodum.meetingservice.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRequestDto {

    private String bookCode;
    private String title;
    private String content;
    private boolean authority;

    public Meeting toEntity(String userCode, String userName) {
        return Meeting.builder()
                .title(this.title)
                .content(this.content)
                .authority(this.authority)
                .userName(userName)
                .userCode(userCode)
                .build();
    }
}
