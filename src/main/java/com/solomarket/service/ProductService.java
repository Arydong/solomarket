package com.solomarket.service;

import com.solomarket.dao.ProductDao;
import com.solomarket.dto.ProductDto;
import com.solomarket.entity.FileEntity;
import com.solomarket.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final FileRepository fileRepository;

    public void registerProductWithFiles(ProductDto productDto, List<MultipartFile> files) {
        productDao.insertProduct(productDto);

        String uploadDir = "C:/upload/product/"; // 경로 구분자는 슬래시(/)를 사용합니다.

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    String originalName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String fileName = uuid + "_" + originalName;
                    String fileUrl = "/upload/product/" + fileName; // 클라이언트에서 접근할 URL

                    // 업로드 폴더 생성 확인
                    File uploadFolder = new File(uploadDir);
                    if (!uploadFolder.exists()) {
                        uploadFolder.mkdirs(); // 폴더가 없으면 생성
                    }
                    // 파일 저장
                    File destinationFile = new File(uploadDir + fileName);
                    file.transferTo(destinationFile);

                    // FileEntity 생성 후 DB에 저장
                    FileEntity fileEntity = FileEntity.builder()
                            .productNo(productDto.getProductNo())
                            .fileName(originalName)
                            .fileUrl(fileUrl)
                            .fileType(file.getContentType())
                            .fileSize(file.getSize())
                            .createdAt(java.time.LocalDateTime.now())
                            .build();

                    fileRepository.save(fileEntity);

                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }
        }
    }
    public List<ProductDto> getLatestProducts() {
        return productDao.selectLatestProducts();
    }

    public List<ProductDto> getProductsBySellerId(int sellerId) {
        return productDao.getProductsBySellerId(sellerId);
    }

    public ProductDto getProductById(int productNo) {
        return productDao.getProductById(productNo);
    }

    public int getSalesCount(int userNo){
        return productDao.countSalesByUser(userNo);
    }

    public void deleteProductById(int productNo) {
        productDao.deleteProduct(productNo);
    }

    public void updateProduct(ProductDto productDto) {
        productDao.updateProduct(productDto);
    }

    public List<ProductDto> searchProducts(String keyword) {
        return productDao.searchProduct(keyword);
    }
}