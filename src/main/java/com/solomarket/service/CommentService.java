package com.solomarket.service;

import com.solomarket.dao.CommentDao;
import com.solomarket.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;

    public void addComment(CommentDto commentDto) {
        commentDao.insertComment(commentDto);
    }

    public List<CommentDto> getCommentTree(int boardId) {
        List<CommentDto> flatList = commentDao.selectCommentsByBoardId(boardId);

        List<CommentDto> treeList = new ArrayList<>();
        for (CommentDto comment : flatList) {
            if (comment.getParentId() == null) { // 부모가 없는 놈만 최상위로
                treeList.add(comment);
            }
        }

        for (CommentDto parent : treeList) {
            for (CommentDto child : flatList) {
                if (child.getParentId() != null && child.getParentId().equals(parent.getCommentId())) {
                    parent.getChildren().add(child);
                }
            }
        }

        return treeList;
    }

    public void deleteComment(int commentId, int userNo) {
        CommentDto comment = commentDao.selectCommentById(commentId);
        if (comment == null || comment.getUserNo() != userNo) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        commentDao.deleteComment(commentId);
    }
}