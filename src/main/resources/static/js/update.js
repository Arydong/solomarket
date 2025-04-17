function updateUserInfo() {
    const nickName = document.getElementById("nickName").value;
    const password = document.getElementById("password").value;
    const confirm = document.getElementById("passwordConfirm").value;

    if (password && password !== confirm) {
        document.getElementById("update-result").innerText = "비밀번호가 일치하지 않습니다.";
        return;
    }

    axios.put("/api/user/update", { nickName, password })
        .then(res => {
            alert("수정되었습니다.");
            window.location.href = "/user/mypage";
        })
        .catch(err => {
            document.getElementById("update-result").innerText = "수정 실패!";
        });
}
