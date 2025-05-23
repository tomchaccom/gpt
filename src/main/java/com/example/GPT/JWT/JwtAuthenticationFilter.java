package com.example.GPT.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // "Bearer " 이후의 토큰 부분 추출
        String email = jwtUtil.getEmailFromToken(token);

        // 여기에 로그 추가
        System.out.println("Token email: " + email);
        // 2. 이메일이 있고, 인증이 아직 안 되어 있으면 인증 처리

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        System.out.println("Token email: " + email);
        System.out.println("Security Context Authentication: " + SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);
    }
}

/*
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // 인증 제외할 경로 목록
    private static final List<String> EXCLUDE_URLS = List.of(
            "/", "/error", "/webjars/", "/api/token", "/search/simple", "/search"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 컨텍스트 경로 제외한 실제 요청 경로
        String path = request.getServletPath();

        // 요청 경로가 인증 제외 경로인지 확인 (startsWith 활용)
        boolean isExcluded = EXCLUDE_URLS.stream().anyMatch(path::startsWith);

        System.out.println("[JwtAuthenticationFilter] Request path: " + path + ", isExcluded: " + isExcluded);

        if (isExcluded) {
            // 인증 없이 바로 다음 필터로 진행
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 토큰 추출
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 토큰 없으면 인증 없이 진행 (또는 인증 실패 처리 가능)
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 이후 토큰 부분만 추출
        String token = authHeader.substring(7);

        // JWT에서 이메일 추출
        String email = jwtUtil.getEmailFromToken(token);

        System.out.println("[JwtAuthenticationFilter] Token email: " + email);

        // 이메일이 있고 SecurityContext에 인증 정보가 없으면 인증 처리
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("[JwtAuthenticationFilter] Authentication set for email: " + email);
        }

        System.out.println("[JwtAuthenticationFilter] Security Context Authentication: "
                + SecurityContextHolder.getContext().getAuthentication());

        // 다음 필터 체인 진행
        filterChain.doFilter(request, response);
    }
}
*/
