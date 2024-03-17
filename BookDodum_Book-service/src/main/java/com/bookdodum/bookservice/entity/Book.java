package com.bookdodum.bookservice.entity;


import com.bookdodum.bookservice.dto.response.BookResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(length = 6300)
    private String content;

    private String publisher;

    private String imageUrl;

    private String isbn;

    private String category;

    public BookResponseDto toDto() {
        return BookResponseDto.builder()
                .title(this.title)
                .author(this.author)
                .content(this.content)
                .publisher(this.publisher)
                .imageUrl(this.imageUrl)
                .isbn(this.isbn)
                .category(this.category)
                .build();
    }
}
