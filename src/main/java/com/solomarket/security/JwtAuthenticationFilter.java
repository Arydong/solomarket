package com.solomarket.security;

import com.solomarket.dao.UserDao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 인증 필터: 모든 요청에서 JWT를 검증하고 인증 정보를 SecurityContext에 설정
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final String jwtCookieName;
    private final UserDao userDao; // ✅ UserDao 주입받아 DB 조회 가능하게!

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = resolveToken(request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userId = jwtTokenProvider.getUserIdFromToken(token);

                // ✅ DB에서 유저 정보 조회 (비밀번호 포함!!)
                CustomUserDetails userDetails = userDao.findByUserId(userId);

                if (userDetails != null) {
                    // ✅ 인증 객체 생성
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    // ✅ SecurityContext에 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("JWT 인증 중 오류 발생: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    // 요청에서 쿠키를 통해 JWT 토큰을 추출하는 메서드
    private String resolveToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (jwtCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}