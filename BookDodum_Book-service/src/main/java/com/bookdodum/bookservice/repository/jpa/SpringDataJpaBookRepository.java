package com.bookdodum.bookservice.repository.jpa;

import com.bookdodum.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SpringDataJpaBookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where :classification = :word" +
            " order by b.title asc")
    List<Book> findBook(String classification, String word);
}
