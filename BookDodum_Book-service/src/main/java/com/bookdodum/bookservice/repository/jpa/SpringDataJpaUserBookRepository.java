package com.bookdodum.bookservice.repository.jpa;

import com.bookdodum.bookservice.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaUserBookRepository extends JpaRepository<UserBook, Long> {

    boolean existsByBook_IdAndUserCode(Long bookId, String userCode);

    @Query("select ub from UserBook ub" +
            " join fetch ub.book" +
            " where ub.book.id = :bookId" +
            " and ub.userCode = :userCode")
    Optional<UserBook> findUserBookDetail(Long bookId, String userCode);

    @Query("select ub from UserBook ub where ub.userCode = :userCode" +
            " and ub.book.id = :bookId" +
            " and ub.startTime = ub.endTime" +
            " order by ub.startTime asc")
    Optional<UserBook> findNotFinishedByBook_IdAndUserCode(Long bookId, String userCode);

    List<UserBook> findAllByUserCode(String userCode);

    @Query("select ub from UserBook ub where ub.book.id = :bookId" +
            " and ub.startTime = ub.endTime" +
            " order by ub.startTime asc")
    List<UserBook> findAllReadWith(Long bookId);

    long deleteByBook_IdAndUserCode(Long bookId, String userCode);

    @Query("select ub from UserBook ub where ub.userCode = :userCode" +
            " and ub.startTime <> ub.endTime" +
            " order by ub.endTime asc")
    List<UserBook> findAllByUserCodeFinish(String userCode);
}
