package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * packageName    : com.solomarket.dto
 * fileName       : ChatDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class ChatDto {
    private int chatId;
    private int productNo;
    private int senderId;
    private int receiverId;
    private String message;
    private LocalDateTime createdAt;
}
