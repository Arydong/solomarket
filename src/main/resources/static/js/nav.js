$(document).ready(function(){
    // ✅ "카테고리 ▼" 버튼 클릭해야 메인 카테고리 나타남
    $("#category-toggle").click(function(event){
        event.stopPropagation();
        $(".main-category").slideToggle(200);
        $(".sub-category, .sub-sub-category").slideUp(200); // 서브 메뉴 닫기
    });

    // ✅ 1단계: 메인 카테고리를 클릭해야 서브 카테고리가 열림
    $(".category-item").click(function(event){
        event.stopPropagation();
        let subMenuId = $(this).attr("data-sub");

        if ($("#" + subMenuId).is(":visible")) {
            $("#" + subMenuId).slideUp(200);
        } else {
            $(".sub-category").slideUp(200); // 다른 메뉴 닫기
            $("#" + subMenuId).slideDown(200);
        }
    });

    // ✅ 2단계: 서브 카테고리를 클릭해야 세부 카테고리가 열림
    $(".sub-category-item").click(function(event){
        event.stopPropagation();
        let subSubMenuId = $(this).attr("data-sub-sub");

        if ($("#" + subSubMenuId).is(":visible")) {
            $("#" + subSubMenuId).slideUp(200);
        } else {
            $(".sub-sub-category").slideUp(200); // 다른 메뉴 닫기
            $("#" + subSubMenuId).slideDown(200);
        }
    });

    // ✅ 다른 곳 클릭하면 모든 메뉴 닫기
    $(document).click(function(){
        $(".main-category, .sub-category, .sub-sub-category").slideUp(200);
    });
});
