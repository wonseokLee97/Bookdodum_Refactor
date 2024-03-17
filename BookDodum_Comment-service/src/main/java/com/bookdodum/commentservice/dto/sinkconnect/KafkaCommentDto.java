package com.bookdodum.commentservice.dto.sinkconnect;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaCommentDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
