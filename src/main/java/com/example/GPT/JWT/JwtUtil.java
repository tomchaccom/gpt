package com.example.GPT.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    /*private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);*/
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 20; // 2시간

    // 256비트 길이(32바이트)의 시크릿 키 (예시용, 실제론 환경변수로 관리하세요)
    private static final String SECRET = "0123456789abcdef0123456789abcdef"; // 32자(256비트)

    private final Key key;

    public JwtUtil() {
        // 고정된 비밀키를 바이트 배열로 만들어서 Key 객체 생성
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            System.out.println("JWT parsing error: " + e.getMessage());
            return null;
        }
    }
}
