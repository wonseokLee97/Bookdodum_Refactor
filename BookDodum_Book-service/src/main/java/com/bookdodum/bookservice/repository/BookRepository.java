package com.bookdodum.bookservice.repository;

import com.bookdodum.bookservice.entity.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    List<Book> findBook(String classification, String word);

    Optional<Book> findById(Long bookId);
}
