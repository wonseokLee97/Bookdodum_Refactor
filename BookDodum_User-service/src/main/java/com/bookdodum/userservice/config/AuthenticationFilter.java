package com.bookdodum.userservice.config;

import com.bookdodum.userservice.dto.request.UserSignInRequestDto;
import com.bookdodum.userservice.dto.response.UserResponseDto;
import com.bookdodum.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment env,
                                String endPoint) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(endPoint, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            // 1. 요청 본문에서 JSON 데이터를 읽어 UserSignInRequestDto로 매핑
            UserSignInRequestDto creds = new ObjectMapper().readValue(
                    request.getInputStream(),
                    UserSignInRequestDto.class);

            // 2. 매핑된 자격 증명을 사용하여 사용자를 인증하도록 인증 매니저에 요청
            //    - UserSignInRequestDto 객체에서 사용자 ID와 비밀번호를 추출하여 UsernamePasswordAuthenticationToken 생성
            //    - new ArrayList<>()는 사용자의 권한(권한이 없는 경우 빈 리스트)을 나타냅니다.
            //    - 사용자가 제공한 자격 증명을 추출하고 AuthenticationManager 에 제출

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    creds.getUserId(),
                    creds.getPassword(),
                    new ArrayList<>()
            );

            Authentication authenticate = getAuthenticationManager().authenticate(token);

            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // JWT Token을 생성하고 response
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userId = ((User) authResult.getPrincipal()).getUsername();
        UserResponseDto userDetails = userService.getUserDetailsByUserId(userId);

        // 비밀 키를 Base64로 인코딩
        String keyBase64Encoded = Base64.getEncoder().encodeToString(env.getProperty("token.secret").getBytes());

        // JWT Token을 생성하는 부분
        String token = Jwts.builder()
                // Payload 세팅: Subject를 유저의 코드로 설정
                .setSubject(userDetails.getUserCode())
                // 토큰 만료 시간 설정 (현재 시간 + 지정된 만료 시간)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                // Signature(서명) 알고리즘 및 비밀 키를 사용하여 서명 생성
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        // 생성된 JWT Token을 HTTP 응답 헤더에 추가
        response.addHeader("token", token);
        // 유저 코드도 헤더에 추가
        response.addHeader("userCode", userDetails.getUserCode());
    }

}
