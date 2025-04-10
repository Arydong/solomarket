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



//    public int getPurchaseCount(int userId){
//        return userDao.countPurchasesByUser(userId);
//    }

//    public double getAvgRating(int userId) {
//        public double getAvgRating(int userId) {
//            Double rating = userDao.getAvgRatingByUser(userId);
//            return rating != null ? rating : 0.0;
//        }        return rating != null ? rating : 0.0;
//    }
}