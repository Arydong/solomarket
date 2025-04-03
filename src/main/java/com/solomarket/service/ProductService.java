package com.solomarket.service;

import com.solomarket.dao.ProductDao;
import com.solomarket.dto.ProductDto;
import com.solomarket.entity.FileEntity;
import com.solomarket.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.solomarket.service
 * fileName       : ProductService
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final FileRepository fileRepository;

    public void registerProductWithFiles(ProductDto productDto, List<MultipartFile> files) {
        productDto.setSellerId(1L); // 🔥 테스트용 sellerId. 실제 로그인 사용자로 변경할 것!
        productDao.insertProduct(productDto); // DB 등록 후 productNo 생성됨

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    String originalName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String fileName = uuid + "_" + originalName;
                    String fileUrl = "/upload/product/" + fileName;

                    // 경로에 저장 생략됨 (형님 파일 저장 로직 넣으셔야 함)

                    FileEntity fileEntity = FileEntity.builder()
                            .productNo(productDto.getProductNo())
                            .fileName(originalName)
                            .fileUrl(fileUrl)
                            .fileType(file.getContentType())
                            .fileSize(file.getSize())
                            .build();

                    fileRepository.save(fileEntity);

                } catch (Exception e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }
        }
    }
}