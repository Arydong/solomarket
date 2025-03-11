package com.solomarket.service;

import com.solomarket.dao.UserDao;
import com.solomarket.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.solomarket.service
 * fileName       : UserService
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public int insertUser(UserDto userDto) {
        return userDao.insertUser(userDto);
    }

}
