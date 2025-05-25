package com.example.GPT.JWT;

// TokenController.java
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.IOException;
import java.util.Map;

@Controller
public class TokenController {

    private final JwtUtil jwtUtil;

    public TokenController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

@GetMapping("/api/token")
    public String generateToken(@AuthenticationPrincipal OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String token = jwtUtil.generateToken(email);
        System.out.println("Generated token: " + token);
        return jwtUtil.generateToken(email);
    }


    @GetMapping("/oauth2/success")
    public void oauthSuccess(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            String token = jwtUtil.generateToken(email);

            // 앱으로 리디렉션
            String redirectUri = "com.example.stopping://oauth2redirect?token=" + token;
            System.out.println("token 생성 = " + token);
            response.sendRedirect(redirectUri);

            // ✅ SecurityContext & 세션 제거
            SecurityContextHolder.clearContext();
            System.out.println("redirectUri 리다이렉션 = " + redirectUri);
            request.getSession().invalidate();


        }
    }

    @GetMapping("/oauth3/success")
    public String oauthSuccess(Authentication authentication, HttpServletRequest request) {
        if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            String token = jwtUtil.generateToken(email);
            String redirectUri = "com.example.stopping://oauth2redirect?token=" + token;

            // 세션 및 SecurityContext 제거
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();

            System.out.println("token 생성 = " + token);
            System.out.println("리다이렉션 URI = " + redirectUri);

            // ✅ Spring redirect 형식으로 앱으로 리디렉트
            return "redirect:" + redirectUri;
        } else {
            // 인증 실패 시 에러 페이지 또는 에러 메시지로 리다이렉트
            return "redirect:/login?error";
        }
    }
}

