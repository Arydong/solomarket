package com.solomarket.dao;

import com.solomarket.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WishlistDao {
    boolean existsByUserNoAndProductNo(int userNo, int productNo);
    void insert(int userNo, int productNo);
    void delete(int userNo, int productNo);
    List<ProductDto> findWishlistProductsByUserNo(int userNo);
}

