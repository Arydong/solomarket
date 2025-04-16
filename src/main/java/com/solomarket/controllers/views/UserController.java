package com.solomarket.controllers.views;

import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.ProductService;
import com.solomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final ProductService productService;

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
        int userNo = customUserDetails.getUserNo();
        String nickname = customUserDetails.getNickname();
        int salesCount = productService.getSalesCount(userNo);

        model.addAttribute("salesCount", salesCount);
        model.addAttribute("nickname", nickname);
        return "/user/mypage";
    }

    @GetMapping("/findIdByPhoneForm")
    public String findIdPage() {
        return "/user/findIdForm";
    }

    @GetMapping("/findPwByPhoneForm")
    public String findPwPage() {
        return "/user/findPwForm";
    }

    @GetMapping("/update")
    public String updateForm() {
        return "/user/updateForm";
    }

}
