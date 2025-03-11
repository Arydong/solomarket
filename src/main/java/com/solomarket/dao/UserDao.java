package com.solomarket.dao;

import com.solomarket.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

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
    int insertUser(UserDto user);
}
