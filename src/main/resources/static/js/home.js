document.addEventListener("DOMContentLoaded", function () {
    const slider = document.querySelector(".slider");
    const slides = slider.querySelectorAll(".slide");
    const totalSlides = slides.length;  // 원본 슬라이드 개수 (예: 3)
    const slideWidth = 1400;
    let currentIndex = 1;
    let isTransitioning = false;
    let slideInterval;

    // 클론 생성: 첫 슬라이드와 마지막 슬라이드 복제
    const firstClone = slides[0].cloneNode(true);
    const lastClone = slides[totalSlides - 1].cloneNode(true);
    slider.appendChild(firstClone);
    slider.insertBefore(lastClone, slides[0]);

    // 복제 후 전체 슬라이드 개수
    const updatedSlides = slider.querySelectorAll(".slide");
    const updatedTotalSlides = updatedSlides.length; // (예: 5)

    // 초기 위치: 실제 첫 슬라이드가 보이도록 (인덱스 1)
    slider.style.transform = `translateX(-${slideWidth}px)`;

    function updateSlidePosition() {
        slider.style.transition = "transform 0.5s ease-in-out";
        slider.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
    }

    function changeSlide(next = true) {
        if (isTransitioning) return;
        isTransitioning = true;
        currentIndex += next ? 1 : -1;
        updateSlidePosition();
    }

    slider.addEventListener("transitionend", (e) => {
        // transform 전환에 대해서만 처리
        if (e.propertyName !== "transform") return;

        if (currentIndex === updatedTotalSlides - 1) {
            // 복제된 첫 슬라이드에 도달한 경우 → 즉시 첫 번째 원본 슬라이드(인덱스 1)로 리셋
            slider.style.transition = "none";
            currentIndex = 1;
            slider.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
            // 강제 리플로우
            void slider.offsetWidth;
        } else if (currentIndex === 0) {
            // 복제된 마지막 슬라이드에 도달한 경우 → 즉시 마지막 원본 슬라이드(인덱스 totalSlides)로 리셋
            slider.style.transition = "none";
            currentIndex = totalSlides;
            slider.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
            void slider.offsetWidth;
        }
        isTransitioning = false;
    });

    document.querySelector(".next-btn").addEventListener("click", () => {
        resetAutoSlide();
        changeSlide(true);
    });

    document.querySelector(".prev-btn").addEventListener("click", () => {
        resetAutoSlide();
        changeSlide(false);
    });

    function startAutoSlide() {
        slideInterval = setInterval(() => {
            changeSlide(true);
        }, 5000);
    }

    function resetAutoSlide() {
        clearInterval(slideInterval);
        startAutoSlide();
    }

    startAutoSlide();
});

document.addEventListener("DOMContentLoaded", function () {
    const mypageLink = document.getElementById('mypage-link');
    const dropdown = document.getElementById('user-dropdown');
    const userMenuContainer = document.getElementById('user-menu-container');

    if (mypageLink) {
        axios.get("/api/auth/check", { withCredentials: true })
            .then(function () {
                // 로그인 상태면 드롭다운 토글 동작
                mypageLink.addEventListener("click", function (e) {
                    e.preventDefault();
                    dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
                });

                // 외부 클릭 시 드롭다운 닫기
                document.addEventListener("click", function (e) {
                    if (!userMenuContainer.contains(e.target)) {
                        dropdown.style.display = "none";
                    }
                });
            })
            .catch(function () {
                // 비로그인 상태: 클릭 시 로그인 폼으로 이동
                mypageLink.addEventListener("click", function (e) {
                    e.preventDefault();
                    window.location.href = "/user/loginForm";
                });
            });
    }
});
