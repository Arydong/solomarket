package com.solomarket.controllers.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.solomarket.controller
 * fileName       : MainController
 * author         : 이동하
 * date           : 25. 3. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 10.        이동하       최초 생성
 */
@Controller
public class MainController {
    @RequestMapping("/")
    public String homepage(){
        return "home";
    }
}
