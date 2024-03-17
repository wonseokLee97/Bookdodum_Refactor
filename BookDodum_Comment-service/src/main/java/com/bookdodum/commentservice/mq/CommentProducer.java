package com.bookdodum.commentservice.mq;

import com.bookdodum.commentservice.dto.response.CommentResponseDto;
import com.bookdodum.commentservice.dto.sinkconnect.Field;
import com.bookdodum.commentservice.dto.sinkconnect.KafkaCommentDto;
import com.bookdodum.commentservice.dto.sinkconnect.Payload;
import com.bookdodum.commentservice.dto.sinkconnect.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "content"),
            new Field("int64", true, "meeting_id"),
            new Field("string", true, "user_code")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("comment")
            .build();


    public void send(String topic, CommentResponseDto commentDto) {

        Payload payload = Payload.builder()
                .content(commentDto.getContent())
                .meeting_id(commentDto.getMeetingId())
                .user_code(commentDto.getUserCode())
                .build();

        KafkaCommentDto kafkaCommentDto = new KafkaCommentDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = mapper.writeValueAsString(kafkaCommentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, jsonInString);

        try {
            SendResult<String, String> result = send.get();
            System.out.println("Record sent to partition " + result.getRecordMetadata().partition() +
                    " with offset " + result.getRecordMetadata().offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        return;
    }
}
