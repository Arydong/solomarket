package com.solomarket.controllers.views;

import com.solomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/regForm")
    public String regForm() {
        return "/user/regForm";
    }
}
