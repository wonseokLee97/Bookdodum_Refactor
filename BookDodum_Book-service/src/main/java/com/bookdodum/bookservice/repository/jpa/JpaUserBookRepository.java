package com.bookdodum.bookservice.repository.jpa;

import com.bookdodum.bookservice.entity.Book;
import com.bookdodum.bookservice.entity.UserBook;
import com.bookdodum.bookservice.repository.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserBookRepository implements UserBookRepository {

    private final SpringDataJpaUserBookRepository userBookRepository;

    @Override
    public boolean existsByBook_IdAndUserCode(Long bookId, String userCode) {
        return userBookRepository.existsByBook_IdAndUserCode(bookId, userCode);
    }

    @Override
    public Optional<UserBook> findUserBookDetail(Long bookId, String userCode) {
        return userBookRepository.findUserBookDetail(bookId, userCode);
    }

    @Override
    public Optional<UserBook> findNotFinishedByBook_IdAndUserCode(Long bookId, String userCode) {
        return userBookRepository.findNotFinishedByBook_IdAndUserCode(bookId, userCode);
    }

    @Override
    public List<UserBook> findAllByUserCode(String userCode) {
        return userBookRepository.findAllByUserCode(userCode);
    }

    @Override
    public List<UserBook> findAllReadWith(Long bookId) {
        return userBookRepository.findAllReadWith(bookId);
    }

    @Override
    public long deleteByBook_IdAndUserCode(Long bookId, String userCode) {
        return userBookRepository.deleteByBook_IdAndUserCode(bookId, userCode);
    }

    @Override
    public List<UserBook> findAllByUserCodeFinish(String userCode) {
        return userBookRepository.findAllByUserCodeFinish(userCode);
    }

    @Override
    public UserBook save(UserBook userBook) {
        return userBookRepository.save(userBook);
    }
}
