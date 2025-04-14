package com.solomarket.controllers.views;

import com.solomarket.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    // ✅ 채팅 목록 페이지 이동
    @GetMapping("/list")
    public String goChatList(HttpServletRequest request, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");

        model.addAttribute("userNo", userDetails.getUserNo());
        model.addAttribute("nickname", userDetails.getNickname());

        return "chat/chatList";
    }

    @GetMapping("/room")
    public String chatRoomPage(@RequestParam int productNo,
                               @RequestParam int sellerId,
                               @RequestParam String chatRoomId,
                               HttpServletRequest request,
                               Model model) {

        CustomUserDetails user = (CustomUserDetails) request.getAttribute("user");

        model.addAttribute("productNo", productNo);
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("buyerId", user.getUserNo());
        model.addAttribute("nickname", user.getNickname());

        return "chat/chatRoom";
    }

    @PostMapping("/room")
    public String enterChatRoom(@RequestParam int productNo,
                                @RequestParam int sellerId,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {

        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");
        int buyerId = userDetails.getUserNo();

        int small = Math.min(buyerId, sellerId);
        int big = Math.max(buyerId, sellerId);

        String chatRoomId = "product-" + productNo + "-buyer-" + small + "-seller-" + big;

        // ✅ 여기서 파라미터를 안전하게 붙임
        redirectAttributes.addAttribute("productNo", productNo);
        redirectAttributes.addAttribute("chatRoomId", chatRoomId);
        redirectAttributes.addAttribute("sellerId", sellerId);

        return "redirect:/chat/room";
    }


}
