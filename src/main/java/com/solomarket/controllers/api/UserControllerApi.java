package com.solomarket.controllers.api;

import com.solomarket.dto.UserDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControllerApi {

    private final UserService userService;

    // 아이디 중복 검사
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<Boolean> checkId(@PathVariable String userId) {
        boolean available = userService.findByUserId(userId) == null;
        return ResponseEntity.ok(available);
    }

    // 닉네임 중복 검사
    @GetMapping("/check-nick")
    public ResponseEntity<Boolean> checkNick(@RequestParam String nickName) {
        boolean available = userService.findByNickName(nickName) == null;
        return ResponseEntity.ok(available);
    }

    // 회원가입 요청
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        int result = userService.insertUser(userDto);
        if (result > 0) {
            return ResponseEntity.ok("회원가입 성공!");
        } else {
            return ResponseEntity.badRequest().body("회원가입 실패!");
        }
    }

    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody UserDto dto) {
        String result = userService.findUserIdByNameAndPhone(dto.getUserName(), dto.getPhone());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body("아이디를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/find-pw")
    public ResponseEntity<String> findPw(@RequestBody UserDto dto) {
        boolean success = userService.verifyUserAndSendTempPassword(dto.getUserId(), dto.getPhone());
        if (success) {
            return ResponseEntity.ok("임시 비밀번호를 발급했습니다.");
        } else {
            return ResponseEntity.status(400).body("정보가 일치하지 않습니다.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserDto userDto,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        userDto.setUserId(userDetails.getUsername());

        boolean result = userService.updateUserNickAndPassword(userDto);
        return result ?
                ResponseEntity.ok("수정 성공") :
                ResponseEntity.badRequest().body("수정 실패");
    }
}
