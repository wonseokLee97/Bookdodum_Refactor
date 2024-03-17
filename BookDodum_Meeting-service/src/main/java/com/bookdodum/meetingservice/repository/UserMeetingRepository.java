package com.bookdodum.meetingservice.repository;


import com.bookdodum.meetingservice.entity.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, Long> {

    Optional<UserMeeting> findByUserCode(String userCode);
}
