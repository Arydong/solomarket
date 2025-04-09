document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("productForm");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = new FormData(form);

        const product = {
            title: form.title.value,
            price: form.price.value,
            content: form.content.value,
            category: form.category.value
        };

        formData.append("product", new Blob([JSON.stringify(product)], { type: "application/json" }));

        axios.post("/api/product/register", formData, {
            headers: { "Content-Type": "multipart/form-data" }
        })
            .then(() => {
                alert("물품이 등록되었습니다!");
                window.location.href = "/";
            })
            .catch(error => {
                console.error("등록 오류:", error);
                alert("등록 중 오류가 발생했습니다.");
            });
    });
});
