package com.solomarket.controllers.views;

import com.solomarket.dto.InquiryDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @RequestMapping("/list")
    public String inquiryList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            int userNo = userDetails.getUserNo();
            model.addAttribute("loginUserNo", userNo);

            // ✅ 문의 목록도 가져와야 함
            List<InquiryDto> inquiryList = inquiryService.getInquiryListByUserNo(userNo);
            model.addAttribute("inquiryList", inquiryList);

        } else {
            model.addAttribute("loginUserNo", null);
            model.addAttribute("inquiryList", Collections.emptyList());
        }

        return "/inquiry/inquiryList";
    }

    @PostMapping("/inquiry")
    public String inquiry(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("loginUserNo", userDetails.getUserNo());
        } else {
            model.addAttribute("loginUserNo", null);
        }
        return "/inquiry/inquiry";
    }

    @GetMapping("/detail/{inquiryId}")
    public String inquiryDetail(@PathVariable int inquiryId, Model model) {
        InquiryDto inquiry = inquiryService.getInquiryById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        return "/inquiry/inquiryDetail";
    }

}
