package com.bookdodum.meetingservice.service;

import com.bookdodum.meetingservice.dto.api.ApiResponseDto;
import com.bookdodum.meetingservice.dto.request.MeetingRequestDto;

public interface MeetingService {

    ApiResponseDto createMeeting(MeetingRequestDto meetingDto, String userCode);
    ApiResponseDto listMeeting();
    ApiResponseDto listMyMeeting(String userCode);
    ApiResponseDto joinMeeting(String userCode, Long meetingId);
    ApiResponseDto deleteMeeting(String userCode, Long meetingId);
    ApiResponseDto getMeeting(Long meetingId);
}
