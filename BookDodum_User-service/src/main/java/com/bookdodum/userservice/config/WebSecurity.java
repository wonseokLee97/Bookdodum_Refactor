package com.bookdodum.userservice.config;

import com.bookdodum.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;

    // 사용자 신원 인증을 위한 구성 메서드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


    // 요청에 대한 사용자 권한을 위한 구성 메서드
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // 0. Actuator 관련 엔드포인트들은 권한으 검사하지 않는다.
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        // 1. 모든 요청과, 특정 IP 주소에서 들어오는 요청만 허용
        http.authorizeRequests()
                .antMatchers("/**") // 모든 요청에 대해서
                .hasIpAddress("172.30.1.27")   // 특정 IP 주소에서 들어오는 요청만 허용 (IP 주소를 지정해야 합니다)
                .and()
                .addFilter(getAuthenticationFilter()); // 사용자 인증 필터 추가



        http.headers().frameOptions().disable();
    }

    // 사용자 인증 필터를 반환하는 메서드
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        // 1. 사용자 인증 필터 생성 및 설정
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(),
                        userService,
                        env,
                        "/signin");

        // authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }
}