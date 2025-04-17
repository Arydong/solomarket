package com.solomarket.controllers.api;

import com.solomarket.security.CustomUserDetails;
import com.solomarket.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistControllerApi {

    private final WishlistService wishlistService;

    @PostMapping("/toggle")
    public Map<String, Boolean> toggleWishlist(@RequestBody Map<String, Object> payload,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        int productNo = Integer.parseInt(payload.get("productNo").toString());
        int userNo = userDetails.getUserNo();

        boolean wished = wishlistService.toggleWishlist(userNo, productNo);

        Map<String, Boolean> result = new HashMap<>();
        result.put("wished", wished);

        return result;
    }
}
