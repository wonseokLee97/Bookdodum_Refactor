package com.bookdodum.commentservice.client;

import com.bookdodum.commentservice.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/users")
    UserResponseDto getUsers(@RequestBody List<String> userCodeList);
}
