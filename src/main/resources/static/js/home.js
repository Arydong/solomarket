// ✅ 슬라이더 기능
document.addEventListener("DOMContentLoaded", function () {
    const slider = document.querySelector(".slider");
    const slides = document.querySelectorAll(".slide");
    const totalSlides = slides.length;
    let currentIndex = 1;
    let isTransitioning = false;
    let slideInterval;

    const firstClone = slides[0].cloneNode(true);
    const lastClone = slides[totalSlides - 1].cloneNode(true);

    slider.appendChild(firstClone);
    slider.insertBefore(lastClone, slides[0]);

    const updatedSlides = document.querySelectorAll(".slide");
    const updatedTotalSlides = updatedSlides.length;

    slider.style.transform = `translateX(-1400px)`;

    function updateSlidePosition() {
        slider.style.transition = "transform 0.5s ease-in-out";
        slider.style.transform = `translateX(-${currentIndex * 1400}px)`;
    }

    function changeSlide(next = true) {
        if (isTransitioning) return;
        isTransitioning = true;
        currentIndex += next ? 1 : -1;
        updateSlidePosition();
    }

    slider.addEventListener("transitionend", () => {
        if (currentIndex === updatedTotalSlides - 1) {
            slider.style.transition = "none";
            currentIndex = 1;
            slider.style.transform = `translateX(-${currentIndex * 1400}px)`;
        }
        if (currentIndex === 0) {
            slider.style.transition = "none";
            currentIndex = totalSlides;
            slider.style.transform = `translateX(-${currentIndex * 1400}px)`;
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
        slideInterval = setInterval(() => changeSlide(true), 5000);
    }

    function resetAutoSlide() {
        clearInterval(slideInterval);
        startAutoSlide();
    }

    startAutoSlide();
});


// ✅ 상품 슬라이드 기능
document.addEventListener("DOMContentLoaded", function () {
    const productGrid = document.querySelector(".product-grid");
    const prevBtn = document.querySelector(".product-prev-btn");
    const nextBtn = document.querySelector(".product-next-btn");

    const products = Array.from({ length: 10 }, (_, i) => `상품 ${i + 1}`);
    let startIndex = 0;
    const visibleCount = 5;

    function renderProducts() {
        productGrid.innerHTML = "";
        for (let i = startIndex; i < startIndex + visibleCount; i++) {
            if (i >= products.length) break;
            const card = document.createElement("div");
            card.classList.add("product-card");
            card.textContent = products[i];
            productGrid.appendChild(card);
        }
    }

    function nextProduct() {
        if (startIndex + visibleCount < products.length) {
            startIndex++;
            renderProducts();
        }
    }

    function prevProduct() {
        if (startIndex > 0) {
            startIndex--;
            renderProducts();
        }
    }

    nextBtn.addEventListener("click", nextProduct);
    prevBtn.addEventListener("click", prevProduct);

    renderProducts();
});


// ✅ 로그인 여부 확인해서 마이페이지/로그인 경로 분기 + 드롭다운 제어까지!!
document.addEventListener("DOMContentLoaded", function () {
    const mypageLink = document.getElementById('mypage-link');
    const dropdown = document.getElementById('user-dropdown');
    const userMenuContainer = document.getElementById('user-menu-container');

    if (mypageLink) {
        axios.get("/api/auth/check", { withCredentials: true })
            .then(function () {
                // ✅ 로그인 상태면 드롭다운 열기 설정
                mypageLink.addEventListener("click", function (e) {
                    e.preventDefault();
                    dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
                });

                // ✅ 외부 클릭 시 드롭다운 닫기
                document.addEventListener("click", function (e) {
                    if (!userMenuContainer.contains(e.target)) {
                        dropdown.style.display = "none";
                    }
                });
            })
            .catch(function () {
                // ❌ 비로그인 시 => 마이페이지 누르면 로그인 폼으로
                mypageLink.addEventListener("click", function (e) {
                    e.preventDefault();
                    window.location.href = "/user/loginForm";
                });
            });
    }
});
