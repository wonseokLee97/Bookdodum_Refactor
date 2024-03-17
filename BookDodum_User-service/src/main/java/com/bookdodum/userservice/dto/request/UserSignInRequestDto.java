package com.bookdodum.userservice.dto.request;

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
public class UserSignInRequestDto {
    @NotNull(message = "유저 아이디를 입력해주세요.")
    @Size(min = 4, max = 10, message = "유저 아이디는 4~10자 사이로 입력해주세요.")
    private String userId; //아이디

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8지 이상 입력해주세요.")
    private String password; //비밀번호
}
