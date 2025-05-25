package com.example.GPT.JWT;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");

            if (email == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "이메일 정보를 가져올 수 없습니다.");
                return;
            }

            String token = jwtUtil.generateToken(email);

            System.out.println("token 생성 완료 = " + token);

            // ✅ 딥링크 URL로 리디렉션
            String redirectUrl = "com.example.stopping://oauth2redirect?token=" + token;

            response.sendRedirect(redirectUrl);

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "OAuth2 인증 사용자 정보가 존재하지 않습니다.");
        }
    }
}
