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
        model.addAttribute("nickname", nickname);
        return "/user/mypage";
    }

}
