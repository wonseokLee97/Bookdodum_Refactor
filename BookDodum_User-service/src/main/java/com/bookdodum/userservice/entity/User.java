package com.bookdodum.userservice.entity;

import com.bookdodum.userservice.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String userId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String encryptedPwd;

    @Column(nullable = false, unique = true)
    private String userCode;


    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .userId(this.userId)
                .name(this.name)
                .userCode(this.userCode)
                .build();
    }
}
