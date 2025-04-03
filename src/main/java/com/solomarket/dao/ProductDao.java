package com.solomarket.dao;

import com.solomarket.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : com.solomarket.dao
 * fileName       : ProductDao
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@Mapper
public interface ProductDao {
    void insertProduct(ProductDto productDto);
}
