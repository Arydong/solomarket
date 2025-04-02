package com.solomarket.service;

import com.solomarket.dao.UserDao;
import com.solomarket.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    private final BCryptPasswordEncoder passwordEncoder; // ✅ 암호화기 주입 완료

    public int insertUser(UserDto userDto) {
        // ✅ 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        // ✅ 기본값 설정
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setRole("USER");
        userDto.setUserStatus("Y");
        userDto.setReportCount(0);

        return userDao.insertUser(userDto);
    }

    public UserDto findByUserId(String userId) {
        return userDao.findById(userId);
    }

    public UserDto findByNickName(String nickName) {
        return userDao.findByNick(nickName);
    }

    public void updateUserProfileImage(String userId, String imagePath) {
        userDao.updateProfileImage(userId, imagePath);
    }
}