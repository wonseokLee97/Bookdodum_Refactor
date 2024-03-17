package com.bookdodum.commentservice.client;

import com.bookdodum.commentservice.dto.api.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "meeting-service")
public interface MeetingServiceClient {

    @GetMapping("/meeting-service/{meetingid}")
    ApiResponseDto<?> getMeeting(@PathVariable("meetingid") Long meetingId);
}
