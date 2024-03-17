package com.bookdodum.commentservice.dto.request;

import com.bookdodum.commentservice.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String content;

    public Comment toEntity(String userCode, Long meetingId) {
        return Comment.builder()
                .content(this.content)
                .meetingId(meetingId)
                .userCode(userCode)
                .build();
    }
}
