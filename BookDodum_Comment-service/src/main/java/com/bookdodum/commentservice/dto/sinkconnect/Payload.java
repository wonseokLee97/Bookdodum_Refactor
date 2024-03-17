package com.bookdodum.commentservice.dto.sinkconnect;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private String content;
    private Long meeting_id;
    private String user_code;
}
