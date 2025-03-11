package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * packageName    : com.solomarket.dto
 * fileName       : ReviewDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class ReviewDto {
    private int reviewId;
    private int transactionId;
    private int reviewerId;
    private int reviewedId;
    private int rating;
    private String content;
    private LocalDate createdAt;
}
