package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.solomarket.dto
 * fileName       : CommentDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class CommentDto {
    private int commentId;
    private int boardId;
    private int userNo;
    private Integer parentId;
    private String content;
    private LocalDateTime createdAt;
    private String nickname;
    private int depth; // 들여쓰기 레벨
    private List<CommentDto> children = new ArrayList<>();
}