package com.solomarket.dao;

import com.solomarket.dto.UserDto;
import com.solomarket.security.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *packageName    : com.solomarket.dao
 * fileName       : UserDao
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Mapper
public interface UserDao {
    CustomUserDetails findByUserId(String userId); // 로그인용
    UserDto findById(String userId); // 아이디 중복 검사
    UserDto findByNick(String nickName); // 닉네임 중복 검사
    int insertUser(UserDto user); // 회원가입
    UserDto findByNameAndPhone(@RequestParam("userName") String userName, @RequestParam("phone") String phone);
    UserDto findByIdAndPhone(String userId, String phone);
    int updatePassword(UserDto userDto); // 비밀번호 변경용
    int updateUserNickAndPassword(UserDto userDto);

}