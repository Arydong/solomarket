package com.solomarket.dao;

import com.solomarket.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    List<BoardDto> getBoardList(BoardDto boardDto);
    BoardDto getBoardById(int boardId);
    void increaseViewCount(int boardId);
    void addBoard(BoardDto boardDto);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(int boardId);
}
