package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * packageName    : com.solomarket.dto
 * fileName       : InquiryDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class InquiryDto {
    private int inquiryId;
    private int userNo;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String status;
    private String responseUserId;
}
