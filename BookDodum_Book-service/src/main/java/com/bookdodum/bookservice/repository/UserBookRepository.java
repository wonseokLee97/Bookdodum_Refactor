package com.bookdodum.bookservice.repository;

import com.bookdodum.bookservice.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository {

    boolean existsByBook_IdAndUserCode(Long bookId, String userCode);

    // 유저도서 상세조회
    Optional<UserBook> findUserBookDetail(Long bookId, String userCode);

    Optional<UserBook> findNotFinishedByBook_IdAndUserCode(Long bookId, String userCode);

    List<UserBook> findAllByUserCode(String userCode);

    List<UserBook> findAllReadWith(Long bookId);

    long deleteByBook_IdAndUserCode(Long bookId, String userCode);

    List<UserBook> findAllByUserCodeFinish(String userCode);

    UserBook save(UserBook build);
}
