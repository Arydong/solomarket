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

firebase.initializeApp(firebaseConfig);
const db = firebase.database();

// ✅ 요소 및 사용자 정보
const productNo = document.getElementById("productNo").value;
const buyerId = parseInt(document.getElementById("buyerId").value); // 숫자 변환 필수!
const nickname = document.getElementById("nickname").value;
const chatRoomId = `product-${productNo}`;

const chatBox = document.getElementById("chat-box");
const chatInput = document.getElementById("chat-input");
const chatForm = document.getElementById("chat-form");

// ✅ 메시지 전송
chatForm.addEventListener("submit", function (e) {
    e.preventDefault();
    const message = chatInput.value.trim();
    if (message === "") return;

    const messageData = {
        senderId: buyerId,
        nickname: nickname,
        message: message,
        timestamp: Date.now()
    };

    db.ref(`chats/product-${productNo}`).push(messageData)
        .then(() => {
            chatInput.value = "";
            appendMessageToUI(messageData); // 즉시 화면에 표시
        })
        .catch((err) => {
            console.error("❌ Firebase push 실패:", err);
            alert("메시지 전송에 실패했습니다.");
        });
});

// ✅ 메시지 실시간 수신
db.ref(`chats/${chatRoomId}`).on("child_added", (data) => {
    const msg = data.val();
    appendMessageToUI(msg);
});

// ✅ 메시지 화면에 출력하는 함수
function appendMessageToUI(msg) {
    const msgElem = document.createElement("div");
    msgElem.classList.add("chat-message");

    if (parseInt(msg.senderId) === buyerId) {
        msgElem.classList.add("my-message");
    } else {
        msgElem.classList.add("their-message");
    }

    msgElem.innerHTML = `<strong>${msg.nickname}:</strong> ${msg.message}`;
    chatBox.appendChild(msgElem);
    chatBox.scrollTop = chatBox.scrollHeight;
}