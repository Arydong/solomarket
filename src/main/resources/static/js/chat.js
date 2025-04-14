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
    console.log("✅ chat.js loaded");

    if (!firebase.apps.length) {
        firebase.initializeApp(firebaseConfig);
    }

    const db = firebase.database();

    // ✅ 서버에서 넘긴 값들만 읽기
    const chatRoomId = document.getElementById("chatRoomId").value;
    const buyerId = parseInt(document.getElementById("buyerId").value);
    const sellerId = parseInt(document.getElementById("sellerId").value);
    const nickname = document.getElementById("nickname").value;

    const chatBox = document.getElementById("chat-box");
    const chatInput = document.getElementById("chat-input");
    const chatForm = document.getElementById("chat-form");

    // ✅ 기존 메시지 로딩 (딱 1번)
    db.ref(`chats/${chatRoomId}`).once("value")
        .then(snapshot => {
            snapshot.forEach(childSnap => {
                const msg = childSnap.val();
                appendMessageToUI(msg);
            });
        });

    // ✅ 실시간 수신
    db.ref(`chats/${chatRoomId}`).on("child_added", (data) => {
        const msg = data.val();
        appendMessageToUI(msg);
    });

    // ✅ 전송 처리
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

        db.ref(`chats/${chatRoomId}`).push(messageData)
            .then(() => {
                chatInput.value = "";
            })
            .catch((err) => {
                console.error("❌ 메시지 전송 실패:", err);
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
