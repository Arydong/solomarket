document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("productForm");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = new FormData(form);

        const product = {
            productNo: form.productNo.value,
            title: form.title.value,
            price: form.price.value,
            content: form.content.value,
            category: form.category.value,
            situation: form.situation.value
        };

        formData.append("product", new Blob([JSON.stringify(product)], { type: "application/json" }));

        axios.post("/api/product/update", formData, {
            headers: { "Content-Type": "multipart/form-data" }
        })
            .then(() => {
                alert("물품이 수정되었습니다!");
                window.location.href = "/";
            })
            .catch(error => {
                alert("수정 중 오류가 발생했습니다.");
            });
    });
});
