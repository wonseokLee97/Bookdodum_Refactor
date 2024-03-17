package com.bookdodum.userservice.dto.request;

import com.bookdodum.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {
    @NotNull(message = "유저 아이디를 입력해주세요.")
    @Size(min = 4, max = 10, message = "유저 아이디는 4 ~ 10자 사이로 입력해주세요.")
    private String userId; //아이디

    @NotNull(message = "활동명을 입력해주세요.")
    @Size(min = 2, max = 10, message = "활동명은 2 ~ 10자 사이로 입력해주세요.")
    private String name; //닉네임

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해주세요.")
    private String password; //비밀번호
    private String userCode; // 고유번호

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .name(this.name)
                .encryptedPwd(this.password)
                .userCode(this.userCode)
                .build();
    }
}
