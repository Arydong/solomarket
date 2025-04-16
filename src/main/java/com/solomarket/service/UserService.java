package com.solomarket.service;

import com.solomarket.dao.UserDao;
import com.solomarket.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

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

    // ✅ [1] 이름 + 전화번호로 아이디 찾기
    public String findUserIdByNameAndPhone(String userName, String phone) {
        UserDto user = userDao.findByNameAndPhone(userName, phone);
        return user != null ? user.getUserId() : null;
    }

    // ✅ [2] 아이디 + 전화번호 일치 시, 임시 비밀번호 발급 후 저장
    public boolean verifyUserAndSendTempPassword(String userId, String phone) {
        UserDto user = userDao.findByIdAndPhone(userId, phone);
        if (user == null) return false;

        String tempPassword = generateTempPassword(); // 예: "aB12cD34"
        String encodedPassword = passwordEncoder.encode(tempPassword);

        // DB에 새 비밀번호 저장
        user.setPassword(encodedPassword);
        userDao.updatePassword(user);

        // TODO: 문자나 이메일로 임시 비밀번호 전송 로직 추가 가능

        System.out.println("임시 비밀번호: " + tempPassword); // 테스트용 출력

        return true;
    }

    // ✅ 임시 비밀번호 생성 함수
    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public boolean updateUserNickAndPassword(UserDto userDto) {
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            String encoded = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encoded);
        }
        return userDao.updateUserNickAndPassword(userDto) > 0;
    }
}