package com.solomarket.controllers.api;

import com.solomarket.dto.UserDto;
import com.solomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.solomarket.controllers.api
 * fileName       : UserControllerApi
 * author         : 이동하
 * date           : 25. 3. 30.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 30.        이동하       최초 생성
 */
@RestController
@RequestMapping("/user-api")
@RequiredArgsConstructor
public class UserControllerApi {

    private final UserService userService;

    // ✅ 아이디 중복 검사
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<Boolean> checkId(@PathVariable String userId) {
        boolean available = userService.findByUserId(userId) == null;
        return ResponseEntity.ok(available);
    }

    // ✅ 닉네임 중복 검사
    @GetMapping("/check-nick")
    public ResponseEntity<Boolean> checkNick(@RequestParam String nickName) {
        boolean available = userService.findByNickName(nickName) == null;
        return ResponseEntity.ok(available);
    }

    // ✅ 회원가입 요청
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        int result = userService.insertUser(userDto);
        if (result > 0) {
            return ResponseEntity.ok("회원가입 성공!");
        } else {
            return ResponseEntity.badRequest().body("회원가입 실패!");
        }
    }
}
