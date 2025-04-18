package com.solomarket.controllers.views;

import com.solomarket.dto.BoardDto;
import com.solomarket.dto.CommentDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.BoardService;
import com.solomarket.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String board(BoardDto boardDto , Model model, HttpServletRequest request) {
        List<BoardDto> boardList = boardService.getBoardList(boardDto);
        model.addAttribute("boardList", boardList);

        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");
        if (userDetails != null) {
            model.addAttribute("loginUserNo", userDetails.getUserNo());
        } else {
            model.addAttribute("loginUserNo", null);
        }
        return "/board/list";
    }

    @GetMapping("/detail/{boardId}")
    public String detail(@PathVariable int boardId, Model model, HttpServletRequest request) {
        boardService.increaseViewCount(boardId);
        BoardDto board = boardService.getBoardById(boardId);
        List<CommentDto> commentTree = commentService.getCommentTree(boardId);

        model.addAttribute("board", board);
        model.addAttribute("commentTree", commentTree);

        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");
        if (userDetails != null) {
            model.addAttribute("loginUserNo", userDetails.getUserNo());
        } else {
            model.addAttribute("loginUserNo", null);
        }

        return "/board/detail";
    }



    @GetMapping("/add")
    public String add() {
        return "/board/addBoard";
    }

    @PostMapping("/add")
    public String addBoard(BoardDto boardDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardDto.setUserNo(customUserDetails.getUserNo());
        boardService.addBoard(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam int boardId, Model model) {
        BoardDto board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        return "/board/updateBoard";
    }

    @PostMapping("/update")
    public String updateBoard(BoardDto boardDto) {
        boardService.updateBoard(boardDto);
        return "redirect:/board/detail/" + boardDto.getBoardId();
    }
    @PostMapping("/delete")
    public String deleteBoard(@RequestParam int boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/list";
    }
}
