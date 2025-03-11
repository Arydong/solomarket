package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;

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
    private int parentId;
    private String content;
    private LocalDateTime createdAt;

}
