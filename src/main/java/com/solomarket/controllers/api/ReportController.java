package com.solomarket.controllers.api;

import com.solomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> reportUser(@RequestBody Map<String, Integer> payload) {
        int userNo = payload.get("userNo");
        userService.reportUser(userNo);
        return ResponseEntity.ok().build();
    }
}
