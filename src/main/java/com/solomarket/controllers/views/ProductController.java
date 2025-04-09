package com.solomarket.controllers.views;

import com.solomarket.dto.ProductDto;
import com.solomarket.dto.UserDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.ProductService;
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

    @GetMapping("/reg")
    public String reg() {
        return "/product/regProduct";
    }

    @GetMapping("/{productNo}")
    public String getProductDetail(@PathVariable int productNo, Model model) {
        ProductDto product = productService.getProductById(productNo);
        model.addAttribute("product", product);
        return "product/productDetail"; // 👉 productDetail.html
    }

    @GetMapping("/list")
    public String getMyProductList(HttpServletRequest request, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user"); // ✅ 이게 맞음!
        int sellerId = userDetails.getUserNo();

        List<ProductDto> myProducts = productService.getProductsBySellerId(sellerId);
        model.addAttribute("myProductList", myProducts);
        return "product/productList";
    }
}