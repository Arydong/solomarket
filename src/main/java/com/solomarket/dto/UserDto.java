package com.solomarket.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * packageName    : com.solomarket.dto
 * fileName       : UserDto
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Data
public class UserDto {
    private int userNo;
    private String userId;
    private String password;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    private Date birth;
    private LocalDateTime createAt;
    private String role;
    private String userImage;
    private String gender;
    private int reportCount;
    private String userStatus;
}
