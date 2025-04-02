package com.solomarket.controllers.api;

import com.solomarket.dto.UserDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    @PostMapping("/upload-profile")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                     HttpServletRequest request) {

        if (file.isEmpty()) {
            return "파일이 비어있습니다.";
        }

        try {
            // 📁 저장 경로 설정
            String uploadDir = request.getServletContext().getRealPath("/upload/profile");
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 📛 파일명 변경
            String originalFilename = file.getOriginalFilename();
            String newFilename = UUID.randomUUID() + "_" + originalFilename;

            // 📦 실제 파일 저장
            File destination = new File(uploadDir, newFilename);
            file.transferTo(destination);

            String imagePath = "upload/profile/" + newFilename;
            userService.updateUserProfileImage(customUserDetails.getUserId(), imagePath);

            return "업로드 성공";

        } catch (IOException e) {
            e.printStackTrace();
            return "업로드 실패: " + e.getMessage();
        }
    }
}