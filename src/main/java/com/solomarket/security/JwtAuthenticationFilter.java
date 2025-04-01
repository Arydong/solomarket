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

/**
 * JWT 인증 필터
 * 모든 요청에서 JWT를 검증하고, 인증 정보를 SecurityContext에 설정합니다!!!
 * 이동하 형님의 명령으로 완성된 코드입니다!!!
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final String jwtCookieName;
    private final UserDao userDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = resolveToken(request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userId = jwtTokenProvider.getUserIdFromToken(token);
                String role = jwtTokenProvider.getRoleFromToken(token);

                // ✅ CustomUserDetails 직접 생성 (DB에서 조회하지 않음! JWT만으로 인증)
                CustomUserDetails userDetails = new CustomUserDetails(userId, null, role);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("JWT 인증 중 오류 발생: ", e);
        }

        filterChain.doFilter(request, response);
    }

    // ✅ 요청에서 JWT 토큰 추출 (쿠키 기반)
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
