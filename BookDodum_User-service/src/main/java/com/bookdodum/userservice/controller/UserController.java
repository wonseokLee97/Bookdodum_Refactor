package com.bookdodum.userservice.controller;

import com.bookdodum.userservice.dto.api.ApiResponseDto;
import com.bookdodum.userservice.dto.request.UserSignUpRequestDto;
import com.bookdodum.userservice.dto.response.UserResponseDto;
import com.bookdodum.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final Environment env;

    @PostMapping("/signup")
    public ApiResponseDto<UserResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto userDto) {
        return userService.signUp(userDto);
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("Token Secret = " + env.getProperty("token.secret") +
                ", Token Expiration Time= " + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/{usercode}")
    public UserResponseDto getUser(@PathVariable("usercode") String userCode) {
        return userService.getUser(userCode);
    }

    @PostMapping("/users")
    public List<UserResponseDto> getUsers(@RequestBody List<String> userCodeList) {
        return userService.getUsers(userCodeList);
    }
}
