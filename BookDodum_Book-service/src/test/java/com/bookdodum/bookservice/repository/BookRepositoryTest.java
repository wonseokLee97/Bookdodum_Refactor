//package com.bookdodum.bookservice.repository;
//
//import com.bookdodum.bookservice.entity.Book;
//import com.bookdodum.bookservice.entity.UserBook;
//import com.bookdodum.bookservice.exception.BookServiceException;
//import com.bookdodum.bookservice.type.ErrorType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.logging.Logger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Import({UserBookRepository.class, BookRepository.class})
//class BookRepositoryTest {
//
//    Logger logger = Logger.getLogger("BookServiceImplTest.class");
//
//    @Autowired
//    private UserBookRepository userBookRepository;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//
//
//    @DisplayName("내 도서 삭제 성공")
//    @Test
//    void updateMyBook() {
//        // given
//        String userCode = "testUserCode";
//        Book book = Book.builder()
//                .title("testTitle")
//                .build();
//
//
//        Book save = bookRepository.save(book);
//
//        Long id = save.getId();
//        logger.info("BookId: " + String.valueOf(id));
//        logger.info("BookTitle: " + save.getTitle());
//
//
//        // when
//        UserBook findUserBook = userBookRepository.findByBook_IdAndUserCode(book.getId(), userCode).orElseThrow(
//                () -> new BookServiceException(ErrorType.NOT_FOUND_USER_BOOK)
//        );
//
//
//        findUserBook.updateEndTime();
//
//
//        // then
//        2(findUserBook.getStartTime(), findUserBook.getEndTime());
//    }
//}