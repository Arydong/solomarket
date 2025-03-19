document.addEventListener("DOMContentLoaded", function () {
    const slider = document.querySelector(".slider");
    const slides = document.querySelectorAll(".slide");
    const totalSlides = slides.length;
    let currentIndex = 1;
    let isTransitioning = false;
    let slideInterval;

    // 첫 번째와 마지막 슬라이드 복제
    const firstClone = slides[0].cloneNode(true);
    const lastClone = slides[totalSlides - 1].cloneNode(true);

    slider.appendChild(firstClone); // 맨 뒤에 첫 번째 슬라이드 복제 추가
    slider.insertBefore(lastClone, slides[0]); // 맨 앞에 마지막 슬라이드 복제 추가

    // 슬라이드 목록 다시 가져오기
    const updatedSlides = document.querySelectorAll(".slide");
    const updatedTotalSlides = updatedSlides.length;

    // 초기 위치 설정
    slider.style.transform = `translateX(-1400px)`;

    function updateSlidePosition() {
        slider.style.transition = "transform 0.5s ease-in-out";
        slider.style.transform = `translateX(-${currentIndex * 1400}px)`;
    }

    function changeSlide(next = true) {
        if (isTransitioning) return;
        isTransitioning = true;

        if (next) {
            currentIndex++;
        } else {
            currentIndex--;
        }

        updateSlidePosition();
    }

    // transition이 끝나면 위치 보정
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

    // 버튼 클릭 이벤트
    document.querySelector(".next-btn").addEventListener("click", () => {
        resetAutoSlide();
        changeSlide(true);
    });

    document.querySelector(".prev-btn").addEventListener("click", () => {
        resetAutoSlide();
        changeSlide(false);
    });

    // 자동 슬라이드 실행
    function startAutoSlide() {
        slideInterval = setInterval(() => changeSlide(true), 5000);
    }

    // 자동 슬라이드 초기화
    function resetAutoSlide() {
        clearInterval(slideInterval);
        startAutoSlide();
    }

    startAutoSlide();
});



document.addEventListener("DOMContentLoaded", function () {
    const productGrid = document.querySelector(".product-grid");
    const prevBtn = document.querySelector(".product-prev-btn");
    const nextBtn = document.querySelector(".product-next-btn");

    // 상품 리스트 (예시 데이터, 실제로는 API에서 가져올 수도 있음)
    const products = Array.from({ length: 10 }, (_, i) => `상품 ${i + 1}`);
    let startIndex = 0; // 처음 보여줄 상품의 시작 인덱스
    const visibleCount = 5; // 한 번에 보이는 상품 개수

    function renderProducts() {
        productGrid.innerHTML = ""; // 기존 상품 초기화
        for (let i = startIndex; i < startIndex + visibleCount; i++) {
            if (i >= products.length) break; // 리스트 끝에 도달하면 중지
            const card = document.createElement("div");
            card.classList.add("product-card");
            card.textContent = products[i]; // 상품 이름 표시
            productGrid.appendChild(card);
        }
    }

    function nextProduct() {
        if (startIndex + visibleCount < products.length) {
            startIndex++; // 시작 인덱스를 오른쪽으로 이동
            renderProducts();
        }
    }

    function prevProduct() {
        if (startIndex > 0) {
            startIndex--; // 시작 인덱스를 왼쪽으로 이동
            renderProducts();
        }
    }

    nextBtn.addEventListener("click", nextProduct);
    prevBtn.addEventListener("click", prevProduct);

    renderProducts(); // 처음 렌더링
});
