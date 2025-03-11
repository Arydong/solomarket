package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * packageName    : com.solomarket.dto
 * fileName       : BoardDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class BoardDto {
    private int boardId;
    private int userNo;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
    private String category;
    private int likeCount;
}
