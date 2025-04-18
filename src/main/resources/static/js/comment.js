// comment.js

// DOM 로딩 완료되면 실행

document.addEventListener("DOMContentLoaded", function () {
    const boardId = document.getElementById("boardId")?.value;

    // 댓글 작성 이벤트
    const commentForm = document.getElementById("commentForm");
    if (commentForm) {
        commentForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const content = document.getElementById("commentContent").value.trim();

            if (!content) {
                alert("댓글 내용을 입력하세요.");
                return;
            }

            axios.post("/comment/add", {
                boardId: boardId,
                content: content,
                parentId: null
            }).then(() => {
                alert("댓글이 작성되었습니다!");
                window.location.reload();
            }).catch(error => {
                console.error("댓글 작성 실패:", error);
                alert("댓글 작성에 실패했습니다.");
            });
        });
    }

    document.querySelectorAll(".reply-btn").forEach(button => {
        button.addEventListener("click", function () {
            console.log('리플버튼 누름');
            const commentId = this.dataset.commentId;
            const existingForm = document.getElementById(`dynamic-reply-form-${commentId}`);

            if (existingForm) {
                existingForm.remove(); // 이미 있으면 지워
                return;
            }

            const replyForm = document.createElement('div');
            replyForm.id = `dynamic-reply-form-${commentId}`;
            replyForm.innerHTML = `
            <textarea class="reply-content" placeholder="답글을 입력하세요"></textarea><br>
            <button type="button" class="submit-reply-btn" data-parent-id="${commentId}">답글 작성</button>
        `;
            const parentComment = document.querySelector(`[data-comment-id="${commentId}"]`).parentNode;
            parentComment.appendChild(replyForm);

            // 추가된 답글 작성 버튼 이벤트 연결
            replyForm.querySelector(".submit-reply-btn").addEventListener("click", function () {
                const content = replyForm.querySelector(".reply-content").value.trim();
                if (!content) {
                    alert("답글 내용을 입력하세요.");
                    return;
                }

                axios.post("/comment/add", {
                    boardId: boardId,
                    content: content,
                    parentId: commentId
                }).then(() => {
                    alert("답글이 작성되었습니다!");
                    window.location.reload();
                }).catch(error => {
                    console.error("답글 작성 실패:", error);
                    alert("답글 작성에 실패했습니다.");
                });
            });
        });
    });


    document.querySelectorAll(".submit-reply-btn").forEach(button => {
        button.addEventListener("click", function () {
            const parentId = this.dataset.parentId;
            const replyForm = document.getElementById(`reply-form-${parentId}`);
            const content = replyForm.querySelector(".reply-content").value.trim();

            if (!content) {
                alert("답글 내용을 입력하세요.");
                return;
            }

            axios.post("/comment/add", {
                boardId: boardId,
                content: content,
                parentId: parentId
            }).then(() => {
                alert("답글이 작성되었습니다!");
                window.location.reload();
            }).catch(error => {
                console.error("답글 작성 실패:", error);
                alert("답글 작성에 실패했습니다.");
            });
        });
    });
});

function deleteComment(button) {
    const commentId = button.getAttribute('data-comment-id');
    if (confirm('정말 이 댓글을 삭제하시겠습니까?')) {
        axios.delete('/comment/' + commentId)
            .then(response => {
                alert('댓글이 삭제되었습니다.');
                location.reload();
            })
            .catch(error => {
                alert('삭제 실패: ' + error.response.data);
            });
    }
}
