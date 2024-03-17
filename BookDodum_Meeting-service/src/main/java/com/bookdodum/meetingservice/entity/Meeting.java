package com.bookdodum.meetingservice.entity;

import com.bookdodum.meetingservice.dto.response.MeetingResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    private boolean authority;

    private String userName;

    private String userCode;

    private String bookCode;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    public MeetingResponseDto toDto() {
        return MeetingResponseDto.builder()
                .id(this.getId())
                .title(this.getTitle())
                .content(this.getContent())
                .authority(this.isAuthority())
                .userName(this.getUserName())
                .userCode(this.getUserCode())
                .bookCode(this.getBookCode())
                .createTime(this.getCreateTime())
                .build();
    }
}