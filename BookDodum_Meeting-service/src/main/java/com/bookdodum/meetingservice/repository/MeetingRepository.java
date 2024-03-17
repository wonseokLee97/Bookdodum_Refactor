package com.bookdodum.meetingservice.repository;

import com.bookdodum.meetingservice.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    boolean existsByUserCodeAndBookCode(String userCode, String bookCode);

    @Query("select m from Meeting m order by m.createTime asc")
    List<Meeting> findAllByAscCreateTime();

    List<Meeting> findAllByUserCode(String UserCode);
}
