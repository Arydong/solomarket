//package com.solomarket.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * packageName    : com.solomarket.security
// * fileName       : JwtTokenProvider
// * author         : 이동하
// * date           : 25. 3. 11.
// * description    :
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 25. 3. 11.        이동하       최초 생성
// */
//@Component
//public class JwtTokenProvider {
//
//    //applicaitno.properties에 저장된 key
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    //applicaitno.properties에 저장된 토큰 지속시간
//    @Value("${jwt.expiration}")
//    private long expiration;
//
//    //토큰 생성
//    public String createToken(String userId, String username, String nickname, String role) {
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + expiration);
//        return Jwts.builder()
//                .setSubject(userId) // user_id 저장
//                .claim("username", username) // username 저장
//                .claim("nickname", nickname) // 닉네임 저장
//                .claim("role", role) // 역할(role) 저장
//                .setIssuedAt(now) // 발급 시간
//                .setExpiration(validity) // 만료 시간
//                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    //토큰 유효성 검사
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        }catch (Exception e) {
//            return false;
//        }
//    }
//
//    //JWT에서 사용자 ID 추출
//    public String getUserIdFromToken(String token) {
//        Claims claims = parseClaims(token);
//        return claims.getSubject(); // "sub" 값을 반환
//    }
//
//    //JWT에서 사용자이름 추출
//    public String getUsernameFromToken(String token) {
//        Claims claims = parseClaims(token);
//        return claims.get("username", String.class);
//    }
//
//    //JWT에서 닉네임 추출
//    public String getNicknameFromToken(String token) {
//        Claims claims = parseClaims(token);
//        return claims.get("nickname", String.class);
//    }
//
//    //JWT에서 역할(Role) 추출
//    public String getRoleFromToken(String token) {
//        Claims claims = parseClaims(token);
//        return claims.get("role", String.class);
//    }
//
//    //JWT 파싱 메서드 (공통 로직)
//    private Claims parseClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//}