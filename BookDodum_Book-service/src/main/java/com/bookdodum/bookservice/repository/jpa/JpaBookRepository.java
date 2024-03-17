package com.bookdodum.bookservice.repository.jpa;

import com.bookdodum.bookservice.entity.Book;
import com.bookdodum.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    private final SpringDataJpaBookRepository bookRepository;

    @Override
    public List<Book> findBook(String classification, String word) {
        return bookRepository.findBook(classification, word);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }
}
