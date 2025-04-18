package com.solomarket.controllers.api;

import com.solomarket.dto.CommentDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentControllerApi {
    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");

        if (userDetails == null) {
            return "로그인이 필요합니다.";
        }

        commentDto.setUserNo(userDetails.getUserNo());
        commentService.addComment(commentDto);

        return "success";
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUserNo());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
