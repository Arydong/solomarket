package com.solomarket.controllers.api;

import com.solomarket.dto.ProductDto;
import com.solomarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/product-api")
@RequiredArgsConstructor
public class ProductControllerApi {
    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(
            @RequestPart("product") ProductDto productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        productService.registerProductWithFiles(productDTO, files);
        return ResponseEntity.ok("물품이 등록되었습니다.");
    }
}
