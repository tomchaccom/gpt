package com.example.GPT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/error", "/webjars/**").permitAll() // 인증 없이 접근 가능한 경로
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .defaultSuccessUrl("/loginSuccess") // 로그인 성공 시 리디렉션될 URL
                        .failureUrl("/loginFailure") // 로그인 실패 시 리디렉션될 URL
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리디렉션될 URL
                        .permitAll()
                );
        return http.build();
    }
}
