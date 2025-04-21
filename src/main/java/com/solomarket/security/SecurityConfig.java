package com.solomarket.security;

import com.solomarket.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDao userDao;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 비활성화
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                        .contentSecurityPolicy(csp -> csp.policyDirectives("frame-src *; script-src * 'unsafe-inline' 'unsafe-eval'; style-src * 'unsafe-inline';"))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/user/loginForm",
                                "/user/findIdByPhoneForm",
                                "/user/findIdByEmailForm",
                                "/user/findPwByPhoneForm",
                                "/user/findPwByEmailForm",
                                "/login",
                                "/logout",
                                "/css/**",
                                "/static/**",
                                "/mapper/**",
                                "/fonts/**",
                                "/images/**",
                                "/js/**",
                                "/api/file/upload"
                        ).permitAll()
                        .requestMatchers(
                                "/user/mypage",
                                "/board/add",
                                "/chat/**",
                                "/product/reg",
                                "/product/add",
                                "/product/update",
                                "/product/delete",
                                "/inquiry/inquiry"
                        ).authenticated() // ✅ 여기는 로그인 필요!
                        .anyRequest().permitAll() // 나머지는 일단 열어놓기
                )
                .formLogin(form -> form
                        .loginPage("/user/loginForm")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider)) // ✅ JWT 쿠키 발급
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .permitAll()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, "keyboardtoken", userDao),
                        UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ✅ 세션 대신 JWT
                )
                .build();
    }

    // ✅ AuthenticationManager 추가 (Spring Security가 로그인 처리할 수 있게 설정)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(new CustomUserDetailsService(userDao))
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    // ✅ CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8090"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // ✅ 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // ✅ BCrypt 비밀번호 암호화기
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
