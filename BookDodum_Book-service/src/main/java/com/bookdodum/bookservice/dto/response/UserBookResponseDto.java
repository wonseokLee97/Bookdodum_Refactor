package com.bookdodum.bookservice.dto.response;

import com.bookdodum.bookservice.entity.Book;
import com.bookdodum.bookservice.entity.UserBook;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UserBookResponseDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String content;
    private String category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public static UserBookResponseDto toDto(UserBook userBook, Book book) {
        return UserBookResponseDto.builder()
                .id(userBook.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .content(book.getContent())
                .category(book.getCategory())
                .startTime(userBook.getStartTime())
                .endTime(userBook.getEndTime())
                .build();
    }
}
