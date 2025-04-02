package com.solomarket.security;

import com.solomarket.dao.UserDao;
import com.solomarket.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final String jwtCookieName;
    private final UserDao userDao;

    public UserInterceptor(JwtTokenProvider jwtTokenProvider, String jwtCookieName, UserDao userDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtCookieName = jwtCookieName;
        this.userDao = userDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserIdFromToken(token);

            // ✅ DB에서 닉네임까지 조회
            UserDto userDto = userDao.findById(userId);

            CustomUserDetails userDetails = new CustomUserDetails(
                    userDto.getUserId(),
                    null,
                    userDto.getRole(),
                    userDto.getNickName(),
                    userDto.getUserImage()
            );

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // ✅ request에도 사용자 정보 세팅
            request.setAttribute("user", userDetails);
        }

        return true;
    }

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