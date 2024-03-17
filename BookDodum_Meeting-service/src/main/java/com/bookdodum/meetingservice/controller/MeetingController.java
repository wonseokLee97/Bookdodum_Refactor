package com.bookdodum.meetingservice.controller;

import com.bookdodum.meetingservice.dto.api.ApiResponseDto;
import com.bookdodum.meetingservice.dto.request.MeetingRequestDto;
import com.bookdodum.meetingservice.service.MeetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MeetingController {
    private final MeetingService meetingService;
//     private final Environment env;

    // 모임 등록
    @PostMapping("{usercode}")
    public ApiResponseDto<?> createMeeting(@RequestBody MeetingRequestDto meetingDto,
                                           @PathVariable("usercode") String userCode) {
        return meetingService.createMeeting(meetingDto, userCode);
    }

    // 전체 모임 조회
    @GetMapping
    public ApiResponseDto<?> listMeeting() {
        return meetingService.listMeeting();
    }

    // 사용자가 참가한 모임 조회
    @GetMapping("{usercode}")
    public ApiResponseDto<?> listMyMeeting(@PathVariable("usercode") String userCode) {
        return meetingService.listMyMeeting(userCode);
    }

    // 사용자가 참가한 모임 조회
    @GetMapping("{meetingid}")
    public ApiResponseDto<?> getMeeting(@PathVariable("meetingid") Long meetingId) {
        return meetingService.getMeeting(meetingId);
    }

    // 미팅 참여
    @PostMapping("{usercode}/{meetingid}")
    public ApiResponseDto<?> joinMeeting(@PathVariable("usercode") String userCode,
                                         @PathVariable("meetingid") Long meetingId) {
        return meetingService.joinMeeting(userCode, meetingId);
    }


    // 모임 삭제
    @DeleteMapping("{usercode}/{meetingid}")
    public ApiResponseDto<?> deleteMeeting(@PathVariable("usercode") String userCode,
                                           @PathVariable("meetingid") Long meetingId) {
        return meetingService.deleteMeeting(userCode, meetingId);
    }
}
