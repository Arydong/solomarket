document.addEventListener("DOMContentLoaded", function () {
    const slider = document.querySelector(".slider");
    const slides = document.querySelectorAll(".slide");
    const totalSlides = slides.length; // 원래 슬라이드 개수 (3)
    let currentIndex = 1; // 첫 번째 실제 슬라이드부터 시작
    let isTransitioning = false;

    // 첫 번째 슬라이드 복제하여 마지막에 추가, 마지막 슬라이드 복제하여 처음에 추가
    const firstClone = slides[0].cloneNode(true);
    const lastClone = slides[totalSlides - 1].cloneNode(true);

    slider.appendChild(firstClone); // 맨 뒤에 첫 번째 복제본 추가
    slider.insertBefore(lastClone, slides[0]); // 맨 앞에 마지막 복제본 추가

    // 복제본 포함한 전체 슬라이드 리스트 업데이트
    const updatedSlides = document.querySelectorAll(".slide");
    const updatedTotalSlides = updatedSlides.length;

    // 초기 위치 설정: 첫 번째 실제 슬라이드가 보이도록 (슬라이드 하나의 너비 1400px)
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

        // transition 시간(0.5초) 이후 경계 확인 후 즉시 위치 재조정
        setTimeout(() => {
            // 오른쪽 끝 (마지막 복제본)에 도달한 경우
            if (currentIndex === updatedTotalSlides - 1) {
                slider.style.transition = "none"; // 애니메이션 없이
                currentIndex = 1; // 첫 번째 실제 슬라이드로 이동
                updateSlidePosition();
            }
            // 왼쪽 끝 (첫 번째 복제본)에 도달한 경우
            if (currentIndex === 0) {
                slider.style.transition = "none";
                currentIndex = totalSlides; // 마지막 실제 슬라이드로 이동
                updateSlidePosition();
            }

            // 짧은 딜레이 후 다시 애니메이션 활성화 및 전환 가능하도록
            setTimeout(() => {
                slider.style.transition = "transform 0.5s ease-in-out";
                isTransitioning = false;
            }, 50);
        }, 500);
    }

    // 버튼 클릭 이벤트
    document.querySelector(".next-btn").addEventListener("click", () => changeSlide(true));
    document.querySelector(".prev-btn").addEventListener("click", () => changeSlide(false));

    // 5초마다 자동 전환
    setInterval(() => changeSlide(true), 5000);
});
