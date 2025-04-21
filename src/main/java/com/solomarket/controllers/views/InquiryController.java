package com.solomarket.controllers.views;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    @RequestMapping("/inquiry")
    public String inquiry() {
        return "/inquiry/inquiryList";
    }
}
