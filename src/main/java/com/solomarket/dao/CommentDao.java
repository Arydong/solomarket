package com.solomarket.dao;

import com.solomarket.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    void insertComment(CommentDto commentDto);

    List<CommentDto> selectCommentsByBoardId(int boardId);

    CommentDto selectCommentById(int commentId);

    void deleteComment(int commentId);
}
