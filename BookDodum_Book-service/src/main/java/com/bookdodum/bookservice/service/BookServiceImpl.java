package com.bookdodum.bookservice.service;

import com.bookdodum.bookservice.client.UserServiceClient;
import com.bookdodum.bookservice.dto.api.ApiResponseDto;
import com.bookdodum.bookservice.dto.api.ResponseUtils;
import com.bookdodum.bookservice.dto.api.SuccessResponse;
import com.bookdodum.bookservice.dto.response.BookResponseDto;
import com.bookdodum.bookservice.dto.response.UserBookResponseDto;
import com.bookdodum.bookservice.dto.response.UserResponseDto;
import com.bookdodum.bookservice.dto.request.BookRequestDto;
import com.bookdodum.bookservice.entity.Book;
import com.bookdodum.bookservice.entity.UserBook;
import com.bookdodum.bookservice.exception.BookServiceUncheckedException;
import com.bookdodum.bookservice.repository.BookRepository;
import com.bookdodum.bookservice.repository.UserBookRepository;
import com.bookdodum.bookservice.type.ErrorType;
import com.bookdodum.bookservice.type.SuccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public ApiResponseDto createMyBook(String userCode, Long bookId) {

        // 유저는 읽는 도서를 등록할 때 동일한 책으로 여러개 생성할 수 없다.
        boolean isExist = userBookRepository.existsByBook_IdAndUserCode(bookId, userCode);

        if (isExist) {
            throw new BookServiceUncheckedException(ErrorType.ALREADY_CREATED_MY_BOOK);
        }

        long startTime = System.currentTimeMillis();

        // 존재하는 도서에 대해서만 내가 읽는 도서를 등록할 수 있다.
        Book book;
        try {
            book = bookRepository.findById(bookId).orElseThrow(
                    () -> new BookServiceUncheckedException(ErrorType.NOT_FOUND_BOOK)
            );
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            log.info("Execution Time (Book retrieval): {}ms", executionTime);
        }

        userBookRepository.save(UserBook.builder()
                .book(book)
                .userCode(userCode)
                .build());

        return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_CREATE_MY_BOOK));
    }


    @Override
    public ApiResponseDto detailMyBook(String userCode, Long bookId) {

        // 유저도서 상세정보를 조회
        UserBook userBook = userBookRepository.findUserBookDetail(bookId, userCode).orElseThrow(
                () -> new BookServiceUncheckedException(ErrorType.NOT_FOUND_USER_BOOK)
        );

        // 해당 유저 도서에 등록된 도서를 가져온다.
        Optional<Book> bookOptional = Optional.ofNullable(userBook.getBook());

        if (!bookOptional.isPresent()) {
            throw new BookServiceUncheckedException(ErrorType.NOT_FOUND_BOOK);
        }

        Book book = bookOptional.get();

        UserBookResponseDto responseDto = UserBookResponseDto.toDto(userBook, book);

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_DETAIL_MY_BOOK,
                responseDto
        ));
    }

    @Override
    public ApiResponseDto listMyBook(String userCode) {
        List<UserBook> userBookList = userBookRepository.findAllByUserCode(userCode);

        List<UserBookResponseDto> responseDtoList = new ArrayList<>();

        for (UserBook userBook : userBookList) {
            responseDtoList.add(UserBookResponseDto.toDto(userBook, userBook.getBook()));
        }

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_LIST_MY_BOOK,
                responseDtoList
        ));
    }

    @Override
    public ApiResponseDto listReadWith(Long bookId) {
        // 같이 책을 읽는 사람들을 찾으려면!!?
        // 1. UserBook 중에서 bookId 가 일치하는 사람들
        // 2. startDate != endDate (아직 읽는 중)
        // 3. startDate 순으로 정렬 (비교적 최근에 읽기 시작한 사람들 위주로 추천)
        List<UserBook> userBookList = userBookRepository.findAllReadWith(bookId);

        // 같이 도서를 읽는 사람이 없는 경우 예외처리.
        if (userBookList.size() == 0) {
            return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_NO_USERS_READING));
        }

        List<String> userCodeList = new ArrayList<>();
        for (UserBook userBook : userBookList) {
            userCodeList.add(userBook.getUserCode());
        }

        List<UserResponseDto> userList = userServiceClient.getUsers(userCodeList);

        // 올바른 타입으로 response 를 전달받지 못했을 경우 예외처리.
        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_LIST_READ_WITH,
                userList
        ));
    }

    @Override
    @Transactional
    public ApiResponseDto deleteMyBook(String userCode, Long bookId) {
        boolean isExist = userBookRepository.existsByBook_IdAndUserCode(bookId, userCode);

        // 읽은 도서 정보가 존재하지 않은 경우 예외 처리.
        if (!isExist) {
            throw new BookServiceUncheckedException(ErrorType.NOT_FOUND_USER_BOOK);
        }

        long deleteCnt = userBookRepository.deleteByBook_IdAndUserCode(bookId, userCode);

        // 삭제가 정상적으로 진행되지 않은 경우 예외 처리.
        if (deleteCnt == 0) {
            throw new BookServiceUncheckedException(ErrorType.ALREADY_DELETED_MY_BOOK);
        }

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_DELETE_MY_BOOK
        ));
    }

    @Override
    @Transactional
    public ApiResponseDto updateMyBook(String userCode, Long bookId) {
        boolean isExist = userBookRepository.existsByBook_IdAndUserCode(bookId, userCode);

        // 읽은 도서 정보가 존재하지 않은 경우 예외 처리.
        if (!isExist) {
            throw new BookServiceUncheckedException(ErrorType.NOT_FOUND_USER_BOOK);
        }

        // 읽기 완료할 유저 도서
        UserBook userBook = userBookRepository.findNotFinishedByBook_IdAndUserCode(bookId, userCode).orElseThrow(
                () -> new BookServiceUncheckedException(ErrorType.NOT_FOUND_UPDATE_USER_BOOK)
        );

        userBook.updateEndTime();


        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_UPDATE_MY_BOOK,
                UserBookResponseDto.toDto(userBook, userBook.getBook())
        ));
    }

    @Override
    public ApiResponseDto listMyFinishedBook(String userCode) {
        List<UserBook> userBookList = userBookRepository.findAllByUserCodeFinish(userCode);

        // 끝낸 도서가 없는 경우 예외처리.
        if (userBookList.size() == 0) {
            return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_NO_FINISH_MY_BOOK));
        }

        List<UserBookResponseDto> responseDtoList = new ArrayList<>();

        for (UserBook userBook : userBookList) {
            responseDtoList.add(UserBookResponseDto.toDto(userBook, userBook.getBook()));
        }

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_LIST_MY_FINISHED_BOOK,
                responseDtoList
        ));
    }

    @Override
    public ApiResponseDto findBook(BookRequestDto requestDto) {
        List<Book> bookList = bookRepository.findBook(requestDto.getClassification(), requestDto.getWord());

        if (bookList.size() == 0) {
            return ResponseUtils.ok(SuccessResponse.of(SuccessType.SUCCESS_NO_LIST_BOOK));
        }

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        for (Book book : bookList) {
            responseDtoList.add(book.toDto());
        }

        return ResponseUtils.ok(SuccessResponse.of(
                SuccessType.SUCCESS_LIST_BOOK,
                responseDtoList
        ));
    }

}

// 동시성 문제를 해결하기 위해, 먼저 데이터 락을 설정해야 한다.
// 락을 설정화는 과정에서 엔티티가 존재하지 않는다면, 이미 삭제 된 경우.
// 락 설정이 완료되었다면, 삭제하려는 엔티티가 존재하는지 확인한다.
// 문제가 없다면 삭제.