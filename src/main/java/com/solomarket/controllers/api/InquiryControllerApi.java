package com.solomarket.controllers.api;

import com.solomarket.dto.InquiryDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryControllerApi {

    private final InquiryService inquiryService;

    @PostMapping("/write")
    public String writeInquiry(@RequestBody InquiryDto inquiryDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "로그인이 필요합니다.";
        }
        inquiryDto.setUserNo(userDetails.getUserNo());
        inquiryService.createInquiry(inquiryDto);
        return "문의가 접수되었습니다.";
    }
}