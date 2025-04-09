package com.solomarket.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.solomarket.controllers.api
 * fileName       : CategoryController
 * author         : 이동하
 * date           : 25. 3. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 18.        이동하       최초 생성
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getCategories() {
        Map<String, List<String>> categories = new HashMap<>();

        categories.put("office", Arrays.asList("멤브레인", "기계식", "무접점", "로우프로파일", "펜타그래프", "기타"));
        categories.put("gaming", Arrays.asList("멤브레인", "기계식", "무접점", "자석축", "로우프로파일", "펜타그래프", "기타"));
        categories.put("outer", Arrays.asList("휴대용 키보드", "미니 키보드", "블루트스 키보드", "속기 키보드", "기타"));

        return ResponseEntity.ok(categories);
    }
}