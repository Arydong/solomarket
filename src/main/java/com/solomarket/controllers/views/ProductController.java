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
    public String getProductDetail(@PathVariable int productNo, HttpServletRequest request, Model model) {
        ProductDto product = productService.getProductById(productNo);
        model.addAttribute("product", product);

        CustomUserDetails userDetails = (CustomUserDetails) request.getAttribute("user");
        if (userDetails != null) {
            model.addAttribute("loginUserNo", userDetails.getUserNo());
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

}