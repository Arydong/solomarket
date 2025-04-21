function submitInquiry() {
    const title = document.getElementById('title').value.trim();
    const content = document.getElementById('content').value.trim();

    if (!title || !content) {
        alert("제목과 내용을 모두 입력해주세요.");
        return;
    }

    axios.post('/api/inquiry/write', {
        title: title,
        content: content
    })
        .then(response => {
            alert(response.data);
            window.location.href = '/inquiry/list';
        })
        .catch(error => {
            console.error(error);
            alert('문의 접수에 실패했습니다.');
        });
}