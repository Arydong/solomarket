document.addEventListener("DOMContentLoaded", function () {
    axios.get("/api/user/info", { withCredentials: true })
        .then(function (res) {
            const data = res.data;
            document.getElementById("profile-username").textContent = data.username;
            document.getElementById("profile-userid").textContent = "@" + data.userId;
            document.getElementById("sales-count").textContent = data.salesCount + "건";
            document.getElementById("purchase-count").textContent = data.purchaseCount + "건";
            document.getElementById("rating").textContent = data.rating + "점";
            if (data.profileImage) {
                document.getElementById("profile-img").src = data.profileImage;
            }
        })
        .catch(function (err) {
            console.error("유저 정보를 불러오지 못했습니다!!", err);
        });
});

document.addEventListener("DOMContentLoaded", function () {
    const profileImg = document.getElementById("profile-img");
    const fileInput = document.getElementById("file-input");

    profileImg.addEventListener("click", () => fileInput.click());

    fileInput.addEventListener("change", function () {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                profileImg.src = e.target.result; // ✅ 미리보기
            };
            reader.readAsDataURL(file);

            let formData = new FormData();
            formData.append("file", file);

            axios.post("/user-api/upload-profile", formData, {
                headers: { "Content-Type": "multipart/form-data" }
            }).then(response => {
                console.log("업로드 성공:", response.data);
            }).catch(error => {
                alert("업로드 실패!!");
            });
        }
    });
});
