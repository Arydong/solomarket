package com.solomarket.security;

import com.solomarket.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final UserDao userDao; // ✅ 필수 추가

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정 적용
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 비활성화
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // ✅ Iframe 허용
                        .contentSecurityPolicy(csp -> csp.policyDirectives("frame-src *; script-src * 'unsafe-inline' 'unsafe-eval'; style-src * 'unsafe-inline';")) // ✅ CSP 정책 적용
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/view/loginForm",
                                "/view/findIdByPhoneForm",
                                "/view/findIdByEmailForm",
                                "/view/findPwByPhoneForm",
                                "/view/findPwByEmailForm",
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
                        .anyRequest().permitAll() // ✅ 모든 요청 허용 (테스트용)
                )
                .formLogin(form -> form
                        .loginPage("/view/loginForm")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider))
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .permitAll()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, "keyboardtoken", userDao), // ✅ 완성형 필터 등록!
                        UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ 허용할 도메인 명확히 지정
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8080",
                "http://192.168.0.2:8080"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // ✅ 쿠키 포함 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}