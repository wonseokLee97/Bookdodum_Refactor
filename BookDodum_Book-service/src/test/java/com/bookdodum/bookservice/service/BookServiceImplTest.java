//package com.bookdodum.bookservice.service;
//
//import com.bookdodum.bookservice.dto.api.ApiResponseDto;
//import com.bookdodum.bookservice.dto.response.UserBookResponseDto;
//import com.bookdodum.bookservice.entity.Book;
//import com.bookdodum.bookservice.entity.UserBook;
//import com.bookdodum.bookservice.exception.BookServiceException;
//import com.bookdodum.bookservice.repository.BookRepository;
//import com.bookdodum.bookservice.repository.UserBookRepository;
//import com.bookdodum.bookservice.type.ErrorType;
//import com.bookdodum.bookservice.type.SuccessType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Supplier;
//import java.util.logging.Logger;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class BookServiceImplTest {
//
//    Logger logger = Logger.getLogger("BookServiceImplTest.class");
//    EntityManager em;
//
//    @InjectMocks
//    private BookServiceImpl bookService;
//
//    // bookService 에 bookRepository, userBookRepository 의존성 주입
//    @Mock
//    private BookRepository bookRepository;
//    @Mock
//    private UserBookRepository userBookRepository;
//
//
//
//    @DisplayName("도서 등록 성공 테스트")
//    @Test
//    void createMyBookSuccess() {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//        Book book = new Book();
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        when(userBookRepository.existsByBook_IdAndUserCode(bookId, userCode)).thenReturn(false);
//
//        // when
//        ApiResponseDto response = bookService.createMyBook(userCode, bookId);
//
//        // then
//        assertEquals(SuccessType.SUCCESS_CREATE_MY_BOOK.getStatusCode(), response.getSuccessResponse().getStatus());
//        verify(bookRepository, times(1)).findById(bookId);
//        verify(userBookRepository, times(1)).save(any(UserBook.class));
//    }
//
//
//    @DisplayName("도서 등록 실패 테스트")
//    @Test
//    void createMyBookFail() {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//
//        when(userBookRepository.existsByBook_IdAndUserCode(bookId, userCode)).thenReturn(true);
//
//        // when
//        BookServiceException exception = assertThrows(BookServiceException.class,
//                () -> bookService.createMyBook(userCode, bookId));
//
//
//        // then
//        assertEquals(ErrorType.ALREADY_CREATED_MY_BOOK, exception.getErrorType());
//
//        verify(userBookRepository, times(1)).existsByBook_IdAndUserCode(bookId, userCode);
//        verify(bookRepository, never()).findById(anyLong());
//        verify(userBookRepository, never()).save(any(UserBook.class));
//    }
//
//
//    @DisplayName("도서 상세정보 조회 성공 테스트")
//    @Test
//    void detailMyBookSuccess() {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//
//        UserBook userBook = mock(UserBook.class);
//        Book book = new Book();
//
//        when(userBookRepository.findByBook_IdAndUserCode(bookId, userCode)).thenReturn(Optional.of(userBook));
//        when(userBook.getBook()).thenReturn(book);
//
//        UserBookResponseDto responseDto = UserBookResponseDto.toDto(userBook, book);
//
//        // when
//        ApiResponseDto response = bookService.detailMyBook(userCode, bookId);
//
//
//        // then
//        assertEquals(SuccessType.SUCCESS_DETAIL_MY_BOOK.getStatusCode(), response.getSuccessResponse().getStatus());
//        assertEquals(responseDto, response.getSuccessResponse().getData());
//
//        verify(userBookRepository, times(1)).findByBook_IdAndUserCode(bookId, userCode);
//    }
//
//
//    @DisplayName("USER_BOOK_NOT_FOUND: 도서 상세정보 조회 실패 테스트")
//    @Test
//    void detailMyBookFail_USER_BOOK_NOT_FOUND() {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//
//        when(userBookRepository.findByBook_IdAndUserCode(bookId, userCode)).thenReturn(Optional.empty());
//
//        // when
//        BookServiceException exception = assertThrows(BookServiceException.class,
//                () -> bookService.detailMyBook(userCode, bookId));
//
//
//        // then
//        assertEquals(ErrorType.NOT_FOUND_USER_BOOK, exception.getErrorType());
//
//        verify(userBookRepository, times(1)).findByBook_IdAndUserCode(bookId, userCode);
//    }
//
//
//    @DisplayName("BOOK_NOT_FOUND: 도서 상세정보 조회 실패 테스트")
//    @Test
//    void detailMyBookFail_BOOK_NOT_FOUND() {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//
//        UserBook userBook = mock(UserBook.class);
//
//        when(userBookRepository.findByBook_IdAndUserCode(bookId, userCode)).thenReturn(Optional.of(userBook));
//        when(userBook.getBook()).thenReturn(null);
//
//
//        // when
//        BookServiceException exception = assertThrows(BookServiceException.class,
//                () -> bookService.detailMyBook(userCode, bookId));
//
//
//        // then
//        assertEquals(ErrorType.NOT_FOUND_BOOK, exception.getErrorType());
//
//        verify(userBookRepository, times(1)).findByBook_IdAndUserCode(bookId, userCode);
//    }
//
//
//    @DisplayName("내 도서 목록 조회 성공")
//    @Test
//    void listMyBookSuccess() {
//        // given
//        String userCode = "testUserCode";
//        List<UserBook> userBookList = new ArrayList<>();
//
//        when(userBookRepository.findAllByUserCode(userCode)).thenReturn(Optional.of(userBookList));
//
//        // when
//        ApiResponseDto response = bookService.listMyBook(userCode);
//
//        // then
//        assertEquals(SuccessType.SUCCESS_LIST_MY_BOOK, response.getSuccessResponse().getStatus());
//    }
//
//
//    @DisplayName("내 도서 삭제 성공")
//    @Test
//    void updateMyBook() {
//        // given
//        String userCode = "testUserCode";
//        Long bookId = 1L;
//
//        Book book = Book.builder()
//                .id(bookId)
//                .title("testBook")
//                .build();
//
//        Optional<UserBook> testUserBook = Optional.ofNullable(UserBook.builder()
//                .id(1L)
//                .userCode(userCode)
//                .book(book)
//                .startTime(LocalDateTime.now())
//                .endTime(LocalDateTime.now())
//                .build());
//
//
//        when(userBookRepository.existsByBook_IdAndUserCode(bookId, userCode))
//                .thenReturn(true);
//        when(userBookRepository.findByBook_IdAndUserCode(bookId, userCode))
//                .thenReturn(testUserBook);
//
//
//
//        // when
//        bookService.updateMyBook(userCode, bookId);
//
//        UserBook findUserBook = userBookRepository.findByBook_IdAndUserCode(book.getId(), userCode).orElseThrow(
//                () -> new BookServiceException(ErrorType.NOT_FOUND_USER_BOOK)
//        );
//
//        logger.info(findUserBook.getId() + "!");
//        logger.info("startTime :" + findUserBook.getStartTime().toString());
//        logger.info("endTime :" + findUserBook.getEndTime().toString());
//
//
//        findUserBook.updateEndTime();
//
//        logger.info("============");
//
//        // then
//        assertNotEquals(findUserBook.getStartTime(), findUserBook.getEndTime());
//
//        logger.info("startTime :" + findUserBook.getStartTime().toString());
//        logger.info("endTime :" + findUserBook.getEndTime().toString());
//    }
//}