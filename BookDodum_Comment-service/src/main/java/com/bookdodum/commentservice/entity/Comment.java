package com.bookdodum.commentservice.entity;

import com.bookdodum.commentservice.dto.request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private Long meetingId;

    private String userCode;

    public void update(CommentRequestDto commentDto) {
        this.content = commentDto.getContent();
    }
}
