package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * packageName    : com.solomarket.dto
 * fileName       : EventDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class EventDto {
    private int eventId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private LocalDateTime createdAt;
}
