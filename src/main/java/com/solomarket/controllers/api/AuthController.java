package com.solomarket.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/check")
    public ResponseEntity<Void> checkLogin(HttpServletRequest request) {
        Object user = request.getAttribute("user"); // UserInterceptor에서 주입된 값

        if (user != null) {
            return ResponseEntity.ok().build(); // ✅ 로그인 상태
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // ❌ 로그인 안 됨
        }
    }
}

