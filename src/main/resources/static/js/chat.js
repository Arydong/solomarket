// ✅ Firebase 실시간 채팅 연동 JS - chat.js

// 🔥 Firebase 설정 (형님 Firebase 콘솔에서 발급받은 값으로 바꿔야 함)
const firebaseConfig = {
    apiKey: "AIzaSyA1HtWF5x_SifeHq1VyBzhH-WjubbQqGAg",
    authDomain: "solomarket-257f5.firebaseapp.com",
    databaseURL: "https://solomarket-257f5-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "solomarket-257f5",
    storageBucket: "solomarket-257f5.firebasestorage.app",
    messagingSenderId: "484437566372",
    appId: "1:484437566372:web:70ee584ca93178c3829731",
    measurementId: "G-5DNCC87XV4"
};

document.addEventListener("DOMContentLoaded", function () {

    if (!firebase.apps.length) {
        firebase.initializeApp(firebaseConfig);
    }

    const db = firebase.database();

    const chatRoomId = document.getElementById("chatRoomId").value;
    const buyerId = parseInt(document.getElementById("buyerId").value);
    const sellerId = parseInt(document.getElementById("sellerId").value);
    const nickname = document.getElementById("nickname").value;

    const chatBox = document.getElementById("chat-box");
    const chatInput = document.getElementById("chat-input");
    const chatForm = document.getElementById("chat-form");

    // ✅ 중복 방지: 기존 리스너 제거 후 단일 실시간 리스너 등록
    const chatRef = db.ref(`chats/${chatRoomId}`);
    chatRef.off();  // 기존 리스너 제거
    chatRef.on("child_added", (data) => {
        const msg = data.val();
        appendMessageToUI(msg);
    });

    // ✅ 메시지 전송 처리
    chatForm.addEventListener("submit", function (e) {
        e.preventDefault();
        sendMessage();
    });

    function sendMessage() {
        const message = chatInput.value.trim();
        if (!message) return;

        const messageData = {
            senderId: buyerId,
            receiverId: sellerId,
            buyerId: buyerId,
            sellerId: sellerId,
            nickname: nickname,
            message: message,
            timestamp: Date.now()
        };

        // ✅ push만 하면 child_added에서 수신되어 UI에 자동 반영됨
        chatRef.push(messageData)
            .then(() => {
                chatInput.value = "";
            })
            .catch((err) => {
                alert("메시지 전송에 실패했습니다.");
            });
    }

    function appendMessageToUI(msg) {
        const msgElem = document.createElement("div");
        msgElem.classList.add("chat-message");

        const isMyMessage = parseInt(msg.senderId) === buyerId;
        msgElem.classList.add(isMyMessage ? "my-message" : "their-message");

        msgElem.innerHTML = `<strong>${msg.nickname}:</strong> ${msg.message}`;
        chatBox.appendChild(msgElem);
        chatBox.scrollTop = chatBox.scrollHeight;
    }
});
