package com.solomarket.dto;

import lombok.Data;

/**
 * packageName    : com.solomarket.dto
 * fileName       : FileDto
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@Data
public class FileDto {
    private Long fileNo;
    private Long productNo;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
    private Integer sortOrder;
}