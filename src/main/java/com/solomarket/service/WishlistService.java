package com.solomarket.service;

import com.solomarket.dao.WishlistDao;
import com.solomarket.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistDao wishlistDao;

    @Transactional
    public boolean toggleWishlist(int userNo, int productNo) {
        if (wishlistDao.existsByUserNoAndProductNo(userNo, productNo)) {
            wishlistDao.delete(userNo, productNo);
            return false; // 찜 해제
        } else {
            wishlistDao.insert(userNo, productNo);
            return true; // 찜 추가
        }
    }
    public boolean isWished(int userNo, int productNo) {
        return wishlistDao.existsByUserNoAndProductNo(userNo, productNo);
    }

    public List<ProductDto> getWishlistProducts(int userNo) {
        return wishlistDao.findWishlistProductsByUserNo(userNo);
    }

}
