package com.solomarket.controllers.views;

import com.solomarket.dto.ProductDto;
import com.solomarket.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
public class ProductController {

    @GetMapping("/reg")
    public String reg() {
        return "/product/regProduct";
    }
}
