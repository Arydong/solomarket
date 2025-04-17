package com.solomarket.controllers.api;

import com.solomarket.dto.FileDto;
import com.solomarket.dto.ProductDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.FileService;
import com.solomarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.solomarket.controllers.api
 * fileName       : ProductControllerApi
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductControllerApi {
    private final ProductService productService;
    private final FileService fileService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(
            @RequestPart("product") ProductDto productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        productDTO.setSellerId(customUserDetails.getUserNo());

        productService.registerProductWithFiles(productDTO, files);
        return ResponseEntity.ok("물품이 등록되었습니다.");
    }
    @GetMapping("/latest")
    public ResponseEntity<List<ProductDto>> getLatestProducts() {
        List<ProductDto> latestProducts = productService.getLatestProducts();
        return ResponseEntity.ok(latestProducts);
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(
            @RequestPart("product") ProductDto productDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        // 상품 정보 수정
        productService.updateProduct(productDto);

        // 파일이 있으면
        if (files != null && !files.isEmpty()) {
            // 1. 기존 파일 삭제
            fileService.deleteFilesByProductNo(productDto.getProductNo());

            // 2. 새 파일 저장
            List<FileDto> fileDtoList = new ArrayList<>();

            for (MultipartFile file : files) {
                try {
                    String originalName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String fileName = uuid + "_" + originalName;
                    String fileUrl = "/upload/product/" + fileName;

                    // 서버에 파일 저장
                    String uploadDir = "C:/upload/product/";
                    File destinationFile = new File(uploadDir + fileName);
                    file.transferTo(destinationFile);

                    // FileDto 생성
                    FileDto fileDto = new FileDto();
                    fileDto.setProductNo(productDto.getProductNo());
                    fileDto.setFileName(fileName);
                    fileDto.setFileUrl(fileUrl);
                    fileDto.setFileType(file.getContentType());
                    fileDto.setFileSize(file.getSize());

                    fileDtoList.add(fileDto);

                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }

            // 3. FileDto 리스트 저장
            fileService.saveFiles(fileDtoList);
        }

        return ResponseEntity.ok("물품이 수정되었습니다.");
    }

}