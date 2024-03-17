package com.bookdodum.bookservice.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class UserBook {

    Thread threadA = new Thread();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private String userCode;

    @CreatedDate
    private LocalDateTime startTime;

    @CreatedDate
    private LocalDateTime endTime;

    public void updateEndTime() {
        this.endTime = LocalDateTime.now();
    }
}
