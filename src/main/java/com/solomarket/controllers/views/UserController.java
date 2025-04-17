package com.solomarket.controllers.views;

import com.solomarket.dto.ProductDto;
import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.ProductService;
import com.solomarket.service.UserService;
import com.solomarket.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * packageName    : com.solomarket.controllers.views
 * fileName       : UserController
 * author         : 이동하
 * date           : 25. 3. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 12.        이동하       최초 생성
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final WishlistService wishlistService;

    @RequestMapping("/loginForm")
    public String lgoinForm() {
        return "/user/loginForm";
    }

    @RequestMapping("/regUserForm")
    public String regForm() {
        return "/user/regUserForm";
    }

    @RequestMapping("/mypage")
    public String mypage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        int userNo = customUserDetails.getUserNo();
        String nickname = customUserDetails.getNickname();
        int salesCount = productService.getSalesCount(userNo);

        model.addAttribute("salesCount", salesCount);
        model.addAttribute("nickname", nickname);
        return "/user/mypage";
    }

    @GetMapping("/findIdByPhoneForm")
    public String findIdPage() {
        return "/user/findIdForm";
    }

    @GetMapping("/findPwByPhoneForm")
    public String findPwPage() {
        return "/user/findPwForm";
    }

    @GetMapping("/update")
    public String updateForm() {
        return "/user/updateForm";
    }

    @GetMapping("/wishlist")
    public String wishlist(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        int userNo = userDetails.getUserNo(); // 로그인한 유저 번호 가져오기
        List<ProductDto> wishlist = wishlistService.getWishlistProducts(userNo); // 찜한 상품 조회

        model.addAttribute("wishlist", wishlist); // 모델에 담기
        return "/user/wishlist"; // templates/user/wishlist.html
    }

}
