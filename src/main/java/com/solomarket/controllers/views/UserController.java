package com.solomarket.controllers.views;

import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.solomarket.controllers.views
 * fileName       : UserController
 * author         : 이동하
 * date           : 25. 3. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 12.        이동하       최초 생성
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping("/loginForm")
    public String lgoinForm() {
        return "/user/loginForm";
    }

    @RequestMapping("/regUserForm")
    public String regForm() {
        return "/user/regUserForm";
    }

    @RequestMapping("/mypage")
    public String mypage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String nickname = customUserDetails.getNickname();
        String profileImage = customUserDetails.getUserImage(); // ← 이거 가져와야지!!!

        if (profileImage == null || profileImage.isEmpty()) {
            profileImage = "/upload/profile/default-profile.png"; // ← 디폴트 경로!!
        } else {
            profileImage = "/upload/profile/" + profileImage; // ← 저장된 파일명 붙여주기
        }

        model.addAttribute("nickname", nickname);
        model.addAttribute("profileImage", profileImage); // ← 추가!!!!!

        return "/user/mypage";
    }

}
