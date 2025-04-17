package com.solomarket.repository;

import com.solomarket.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.solomarket.repository
 * fileName       : FileRepository
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByProductNo(int productNo);
    int deleteByProductNo(int productNo);
}
