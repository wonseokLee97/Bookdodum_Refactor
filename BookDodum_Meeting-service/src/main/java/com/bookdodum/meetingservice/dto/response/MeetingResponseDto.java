package com.bookdodum.meetingservice.dto.response;

import com.bookdodum.meetingservice.entity.Meeting;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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



    public static MeetingResponseDto toDto(Meeting meeting) {
        return MeetingResponseDto.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .content(meeting.getContent())
                .authority(meeting.isAuthority())
                .userName(meeting.getUserName())
                .userCode(meeting.getUserCode())
                .bookCode(meeting.getBookCode())
                .createTime(meeting.getCreateTime())
                .build();
    }
}
