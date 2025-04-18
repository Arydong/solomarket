package com.solomarket.service;

import com.solomarket.dao.BoardDao;
import com.solomarket.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardDao boardDao;

    public List<BoardDto> getBoardList(BoardDto boardDto) {
        return boardDao.getBoardList(boardDto);
    }

    public BoardDto getBoardById(int boardId) {
        return boardDao.getBoardById(boardId);
    }

    public void increaseViewCount(int boardId) {
        boardDao.increaseViewCount(boardId);
    }

    public void addBoard(BoardDto boardDto) {
        boardDao.addBoard(boardDto);
    }

    public void updateBoard(BoardDto boardDto) {
        boardDao.updateBoard(boardDto);
    }
    public void deleteBoard(int boardId) {
        boardDao.deleteBoard(boardId);
    }
}
