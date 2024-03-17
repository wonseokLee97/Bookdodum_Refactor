package com.bookdodum.userservice.service;

import com.bookdodum.userservice.dto.api.ApiResponseDto;
import com.bookdodum.userservice.dto.api.ResponseUtils;
import com.bookdodum.userservice.dto.api.SuccessResponse;
import com.bookdodum.userservice.dto.request.UserSignUpRequestDto;
import com.bookdodum.userservice.dto.response.UserResponseDto;
import com.bookdodum.userservice.entity.User;
import com.bookdodum.userservice.exception.UserServiceException;
import com.bookdodum.userservice.repository.UserRepository;
import com.bookdodum.userservice.type.ErrorType;
import com.bookdodum.userservice.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder pwdEncoder;

    @Override
    public ApiResponseDto signUp(UserSignUpRequestDto userDto) {
        boolean isExist = userRepository.existsByUserId(userDto.getUserId());

        if (isExist) {
            throw new UserServiceException(ErrorType.ALREADY_CREATED_USER_ID);
        }

        userDto.setPassword(pwdEncoder.encode(userDto.getPassword()));
        userDto.setUserCode(UUID.randomUUID().toString());


        // DTO -> UserEntity 변환
        userRepository.save(userDto.toEntity());

        UserResponseDto responseDto = UserResponseDto.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .userCode(userDto.getUserCode())
                .build();


        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_SIGN_UP,
                responseDto));
    }


    @Override
    public UserResponseDto getUser(String userCode) {
        User user = userRepository.findByUserCode(userCode).orElseThrow(
                () -> new UserServiceException(ErrorType.NOT_FOUND_USER)
        );

        UserResponseDto responseDto = user.toDto();

        return responseDto;
    }

    @Override
    public List<UserResponseDto> getUsers(List<String> userCodeList) {
        List<UserResponseDto> responseDtoList = new ArrayList<>();

        for (String userCode : userCodeList) {
            Optional<User> userOptional = userRepository.findByUserCode(userCode);

            if (userOptional.isEmpty()) {
                responseDtoList.add(
                        UserResponseDto.builder()
                                .userId("알수없는 누군가")
                                .name("알수없는 누군가")
                                .userCode(userCode)
                                .build()
                );
            } else {
                User user = userOptional.get();

                responseDtoList.add(user.toDto());
            }
        }

        return responseDtoList;
    }


//    @Override
//    public ApiResponseDto singIn(UserSignInRequestDto userDto) {
//        User user = userRepository.findByUserid(userDto.getUserId());
//
//        if (user == null || !pwdEncoder.matches(userDto.getPassword(), user.getEncryptedPwd())) {
//            throw new NotValidSingInRequestException(
//                    HttpStatus.BAD_REQUEST,
//                    "입력하신 ID나 비밀번호를 다시 확인해주세요");
//        }
//
//
//        return ResponseUtils.ok(userDto);
//    }

    @Override
    public UserResponseDto getUserDetailsByUserId(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(userId);
        }

        User user = userOptional.get();

        return UserResponseDto.builder()
                .userCode(user.getUserCode())
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }



    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        // 해당 유저가 존재하지 않는다면,
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(userId);
        }

        User user = userOptional.get();

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>()
        );
    }

}
