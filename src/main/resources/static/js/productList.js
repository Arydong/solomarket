document.addEventListener("DOMContentLoaded", function () {
    const chatLink = document.getElementById("chat-link");
    const sellItemLink = document.getElementById("sell-item-link");
    const mypageLink = document.getElementById("mypage-link");
    const dropdown = document.getElementById("user-dropdown");
    const userMenuContainer = document.getElementById("user-menu-container");

    // 로그인 여부 체크 (withCredentials 옵션으로 쿠키/세션 정보를 포함)
    axios.get("/api/auth/check", {withCredentials: true})
        .then(function () {
            // 로그인 상태인 경우

            chatLink.addEventListener("click", function (e) {
                e.preventDefault();
                window.location.href = "/chat/list";
            });

            sellItemLink.addEventListener("click", function (e) {
                e.preventDefault();
                // 판매하기(상품 등록) 페이지 URL (예: /product/reg)로 이동
                window.location.href = "/product/reg";
            });

            mypageLink.addEventListener("click", function (e) {
                e.preventDefault();
                // 로그인 상태에서는 마이페이지 버튼을 누르면 드롭다운 토글 처리
                dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
            });

            // 드롭다운 외부 클릭 시 드롭다운 닫기 처리
            document.addEventListener("click", function (e) {
                if (!userMenuContainer.contains(e.target)) {
                    dropdown.style.display = "none";
                }
            });
        })
        .catch(function () {
            // 비로그인 상태인 경우: 모든 버튼을 클릭 시 로그인 폼으로 이동합니다.
            const redirectToLogin = function (e) {
                e.preventDefault();
                window.location.href = "/user/loginForm";
            };

            chatLink.addEventListener("click", redirectToLogin);
            sellItemLink.addEventListener("click", redirectToLogin);
            mypageLink.addEventListener("click", redirectToLogin);
        });
});
