package com.bookdodum.meetingservice.service;

import com.bookdodum.meetingservice.client.UserServiceClient;
import com.bookdodum.meetingservice.dto.api.ApiResponseDto;
import com.bookdodum.meetingservice.dto.api.ResponseUtils;
import com.bookdodum.meetingservice.dto.api.SuccessResponse;
import com.bookdodum.meetingservice.dto.request.MeetingRequestDto;
import com.bookdodum.meetingservice.dto.response.MeetingResponseDto;
import com.bookdodum.meetingservice.dto.response.UserResponseDto;
import com.bookdodum.meetingservice.entity.Meeting;
import com.bookdodum.meetingservice.entity.UserMeeting;
import com.bookdodum.meetingservice.exception.MeetingServiceException;
import com.bookdodum.meetingservice.repository.MeetingRepository;
import com.bookdodum.meetingservice.repository.UserMeetingRepository;
import com.bookdodum.meetingservice.type.ErrorType;
import com.bookdodum.meetingservice.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserMeetingRepository userMeetingRepository;
    private final UserServiceClient userServiceClient;
    private final RestTemplate restTemplate;
    private final Environment env;

    @Override
    public ApiResponseDto createMeeting(MeetingRequestDto meetingDto, String userCode) {

        // 유저는 모임을 생성할 때 동일한 책으로 여러 모임을 생성할 수 없다.
        // 모임중 현재 유저가 생성하려는 책의 모임이 있는지 확인한다.
        boolean exist = meetingRepository.existsByUserCodeAndBookCode(userCode, meetingDto.getBookCode());

        if (exist) {
            throw new MeetingServiceException(ErrorType.ALREADY_CREATED_MEETING);
        }


        // FeignClient 로 유저의 정보를 찾아야 한다.
        ApiResponseDto<?> responseDtoByUserService = userServiceClient.getUser(userCode);

        // 유저의 정보가 존재하는가?
        if (!(responseDtoByUserService.getResponse() instanceof SuccessResponse)) {
            throw new MeetingServiceException(ErrorType.NOT_FOUND_USER);
        }

        SuccessResponse response = (SuccessResponse) responseDtoByUserService.getResponse();
        UserResponseDto userData = (UserResponseDto) response.getData();


        // 추후에 Kafka 로 변경??
        // 1. 모임 저장
        Meeting meeting = meetingRepository.save(meetingDto.toEntity(userCode, userData.getName()));

        // 2. 유저 모임 저장
        userMeetingRepository.save(
                UserMeeting.builder()
                        .userCode(userCode)
                        .meeting(meeting)
                        .build());

        MeetingResponseDto responseDto = meeting.toDto();

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_CREATE_MEETING,
                responseDto));
    }


    @Override
    public ApiResponseDto listMeeting() {
        List<Meeting> meetingList = meetingRepository.findAllByAscCreateTime();
        List<MeetingResponseDto> responseDtoList = new ArrayList<>();

        for (Meeting meeting : meetingList) {
            responseDtoList.add(MeetingResponseDto.toDto(meeting));
        }

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_INQUIRY_ALL_MEETING,
                responseDtoList));
    }


    @Override
    public ApiResponseDto listMyMeeting(String userCode) {
        List<Meeting> meetingList = meetingRepository.findAllByUserCode(userCode);
        List<MeetingResponseDto> responseDtoList = new ArrayList<>();

        for (Meeting meeting : meetingList) {
            responseDtoList.add(MeetingResponseDto.toDto(meeting));
        }

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_INQUIRY_MY_MEETING,
                responseDtoList));
    }

    @Override
    public ApiResponseDto joinMeeting(String userCode, Long meetingId) {

        // 존재하는 모임인가?
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new MeetingServiceException(ErrorType.NOT_FOUND_MEETING)
        );

        // 이미 참여한 모임인가?
        userMeetingRepository.findByUserCode(userCode).orElseThrow(
                () -> new MeetingServiceException(ErrorType.ALREADY_JOINED_MEETING)
        );


        userMeetingRepository.save(UserMeeting.builder()
                .userCode(userCode)
                .meeting(meeting)
                .build());

        return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_JOIN_MEETING));
    }

    @Override
    public ApiResponseDto deleteMeeting(String userCode, Long meetingId) {
        // 모임은 생성자만 삭제할 수 있다.
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new MeetingServiceException(ErrorType.NOT_FOUND_MEETING)
        );

        if (!meeting.getUserCode().equals(userCode)) {
            throw new MeetingServiceException(ErrorType.INVALID_AUTHORIZATION);
        }

        meetingRepository.deleteById(meetingId);

        return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_DELETE_MEETING));
    }

    @Override
    public ApiResponseDto getMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new MeetingServiceException(ErrorType.NOT_FOUND_MEETING)
        );

        MeetingResponseDto responseDto = meeting.toDto();

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_INQUIRY_MEETING,
                responseDto));
    }
}