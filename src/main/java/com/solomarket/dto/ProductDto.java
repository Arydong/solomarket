package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * packageName    : com.solomarket.dto
 * fileName       : ProductDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class ProductDto {
    private int productNo;
    private int sellerId;
    private String title;
    private int price;
    private String content;
    private String productImage;
    private String situation;
    private String category;
    private LocalDateTime createdAt;
    private int viewCount;
}
