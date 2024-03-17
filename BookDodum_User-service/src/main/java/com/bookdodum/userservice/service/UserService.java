package com.bookdodum.userservice.service;

import com.bookdodum.userservice.dto.api.ApiResponseDto;
import com.bookdodum.userservice.dto.request.UserSignInRequestDto;
import com.bookdodum.userservice.dto.request.UserSignUpRequestDto;
import com.bookdodum.userservice.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    ApiResponseDto signUp(UserSignUpRequestDto dto);
    UserResponseDto getUserDetailsByUserId(String userId);
    UserResponseDto getUser(String userCode);
    List<UserResponseDto> getUsers(List<String> userCodeList);
}
