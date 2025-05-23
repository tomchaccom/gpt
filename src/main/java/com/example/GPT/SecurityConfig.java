package com.example.GPT;

import com.example.GPT.JWT.JwtAuthenticationFilter;
import com.example.GPT.JWT.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler(JwtUtil jwtUtil) {
        return new OAuth2SuccessHandler(jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           OAuth2SuccessHandler oAuth2SuccessHandler) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})  // CORS 설정은 WebMvcConfigurer에서 따로 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS preflight 허용
                        .requestMatchers(HttpMethod.GET, "/search", "/search/simple").permitAll()
                        .requestMatchers(HttpMethod.POST, "/search", "/search/simple").permitAll()
                        .requestMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2SuccessHandler)
                        .failureUrl("/loginFailure")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

