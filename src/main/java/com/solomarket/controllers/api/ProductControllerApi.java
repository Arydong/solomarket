package com.solomarket.controllers.api;

import com.solomarket.dto.ProductDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(
            @RequestPart("product") ProductDto productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        System.out.println("🔥 로그인 유저 ID: " + customUserDetails.getUserNo());
        System.out.println("🔥 받은 DTO 초기값: " + productDTO);

        productDTO.setSellerId(customUserDetails.getUserNo());
        System.out.println("🔥 DTO에 셋팅 후 sellerId: " + productDTO.getSellerId());

        productService.registerProductWithFiles(productDTO, files);
        return ResponseEntity.ok("물품이 등록되었습니다.");
    }
    @GetMapping("/latest")
    public ResponseEntity<List<ProductDto>> getLatestProducts() {
        List<ProductDto> latestProducts = productService.getLatestProducts();
        return ResponseEntity.ok(latestProducts);
    }
}