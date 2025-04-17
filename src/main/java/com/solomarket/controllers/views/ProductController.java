package com.solomarket.controllers.views;

import com.solomarket.dto.ProductDto;
import com.solomarket.dto.UserDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.ProductService;
import com.solomarket.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * packageName    : com.solomarket.controllers.views
 * fileName       : ProductController
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final WishlistService wishlistService;

    @GetMapping("/reg")
    public String reg() {
        return "/product/regProduct";
    }

    @GetMapping("/{productNo}")
    public String getProductDetail(@PathVariable int productNo, HttpServletRequest request, Model model) {
        ProductDto product = productService.getProductById(productNo);
        model.addAttribute("product", product);

        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");
        if (userDetails != null) {
            model.addAttribute("loginUserNo", userDetails.getUserNo());

            // ⭐ 추가: 로그인한 유저가 이 상품을 찜했는지 조회
            boolean isWished = wishlistService.isWished(userDetails.getUserNo(), productNo);
            model.addAttribute("isWished", isWished);
        } else {
            model.addAttribute("isWished", false);  // 로그인 안 했으면 기본 false
        }

        return "product/productDetail";
    }


    @GetMapping("/list")
    public String getMyProductList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        int sellerId = userDetails.getUserNo();

        List<ProductDto> myProducts = productService.getProductsBySellerId(sellerId);
        model.addAttribute("myProductList", myProducts);
        return "product/productList";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productNo") int productNo) {
        productService.deleteProductById(productNo);
        return "redirect:/product/list";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam int productNo, Model model) {
        ProductDto product = productService.getProductById(productNo);
        model.addAttribute("product", product);
        return "/product/updateProduct";
    }

    @PostMapping("/update")
    public String updateProduct(ProductDto productDto) {
        productService.updateProduct(productDto);
        return "redirect:/product/list";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<ProductDto> searchResults = productService.searchProducts(keyword);
        model.addAttribute("products", searchResults);
        return "/product/searchResult";
    }

}