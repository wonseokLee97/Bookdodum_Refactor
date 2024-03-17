package com.bookdodum.meetingservice.mq;

import com.bookdodum.meetingservice.dto.request.CommentRequestDto;
import com.bookdodum.meetingservice.entity.Comment;
import com.bookdodum.meetingservice.repository.CommentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final CommentRepository commentRepository;

    @KafkaListener(topics = "comment-topic")
    public void updateComment(String message) {
        log.info("kafka Message: -> " + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String method = (String) map.get("method");

        Long commentId = (Long) map.get("commentId");
        Long meetingId = (Long) map.get("meetingId");
        String userCode = (String) map.get("userCode");
        String content = (String) map.get("content");

        Comment comment;

        switch (method) {
            case "post":
                comment = Comment.builder()
                        .content(content)
                        .commentId(commentId)
                        .meetingId(meetingId)
                        .userCode(userCode)
                        .build();

                commentRepository.save(comment);

            case "put":
                CommentRequestDto requestDto = (CommentRequestDto) map.get("dto");
                comment = commentRepository.findByCommentId(commentId);
                comment.update(requestDto);

            case "delete":
                map.get()

        }
    }
}
