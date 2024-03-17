package com.bookdodum.meetingservice.client;

import com.bookdodum.meetingservice.dto.api.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/user-service/user")
    ApiResponseDto<?> getUser(@RequestBody String userCode);
}
