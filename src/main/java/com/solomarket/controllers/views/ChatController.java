package com.solomarket.controllers.views;

import com.solomarket.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    // ✅ 채팅 리스트로 이동
    @GetMapping("/list")
    public String goChatList(HttpServletRequest request, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");

        model.addAttribute("userNo", userDetails.getUserNo());
        model.addAttribute("nickname", userDetails.getNickname());
        return "chat/chatList"; // chatList.html
    }

    @GetMapping("/room")
    public String chatRoomPage(@RequestParam int productNo,
                               @RequestParam int sellerId,
                               HttpServletRequest request,
                               Model model) {
        CustomUserDetails user = (CustomUserDetails) request.getAttribute("user");

        model.addAttribute("productNo", productNo);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("buyerId", user.getUserNo());
        model.addAttribute("nickname", user.getNickname());

        return "chat/chatList";
    }

    @PostMapping("/room")
    public String enterChatRoom(@RequestBody Map<String, Object> body, HttpServletRequest request, Model model) {
        int productNo = (int) body.get("productNo");
        int sellerId = (int) body.get("sellerId");

        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");

        model.addAttribute("productNo", productNo);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("buyerId", userDetails.getUserNo());
        model.addAttribute("nickname", userDetails.getNickname());

        return "chat/chatRoom";
    }
}
