$(document).ready(function(){
    let categories = {}; // ✅ 서버에서 받아올 카테고리 데이터 저장

    // ✅ 서버에서 카테고리 데이터 가져오기
    function fetchCategories() {
        axios.get('/api/category')
            .then(response => {
                categories = response.data;
            })
            .catch(error => console.error("❌ 카테고리 데이터를 불러오지 못했습니다!", error));
    }

    // ✅ "카테고리 ▼" 클릭하면 메인 카테고리 토글
    $("#category-toggle").click(function(event){
        event.stopPropagation();
        $(".main-category").slideToggle(200);
        $(".sub-category").slideUp(200); // 서브 카테고리 닫기
    });

    // ✅ 메인 카테고리 클릭하면 서브 카테고리 동적 추가
    $(".category-item").click(function(event){
        event.stopPropagation();
        let selectedCategory = $(this).attr("data-category");
        let subCategoryMenu = $("#sub-category-container");

        subCategoryMenu.empty().hide(); // 기존 서브 카테고리 초기화

        if (categories[selectedCategory]) {
            categories[selectedCategory].forEach(sub => {
                subCategoryMenu.append(`<li class="sub-category-item">${sub}</li>`);
            });
            subCategoryMenu.slideDown(200); // 서브 카테고리 열기
        }
    });

    // ✅ 페이지 로드 시 카테고리 불러오기
    fetchCategories();

    // ✅ 다른 곳 클릭하면 모든 메뉴 닫기
    $(document).click(function(){
        $(".main-category, .sub-category").slideUp(200);
    });
});
