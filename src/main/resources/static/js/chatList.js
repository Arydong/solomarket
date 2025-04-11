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
if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
}

const db = firebase.database();

// ✅ 현재 로그인한 사용자 ID, 닉네임, 컨테이너 요소
const currentUserId = document.getElementById("currentUserId")?.value;
const currentNickname = document.getElementById("nickname")?.value;
const chatListContainer = document.getElementById("chat-list");

if (!currentUserId || !chatListContainer) {
    console.error("❌ 사용자 정보 또는 채팅 리스트 컨테이너가 없습니다.");
}

// ✅ 전체 채팅방 조회 및 렌더링
const chatRoomsRef = db.ref("chats");
chatRoomsRef.once("value", (snapshot) => {
    snapshot.forEach((roomSnap) => {
        const chatRoomId = roomSnap.key; // ex) product-123
        const messages = roomSnap.val();

        if (!messages) return;

        const messageKeys = Object.keys(messages);
        const lastMessage = messages[messageKeys[messageKeys.length - 1]];

        // ✅ 본인이 참여한 채팅방만 표시
        const isParticipant = Object.values(messages).some(msg => msg.senderId == currentUserId);
        if (!isParticipant) return;

        const productNo = chatRoomId.split("-")[1];
        const sellerId = lastMessage.senderId;

        const chatItem = document.createElement("div");
        chatItem.classList.add("chat-item");
        chatItem.innerHTML = `
            <a href="#" data-product-no="${productNo}" data-seller-id="${sellerId}" class="chat-room-link">
                <strong>${lastMessage.nickname}</strong><br>
                <span>${lastMessage.message}</span>
            </a>
        `;
        chatListContainer.appendChild(chatItem);
    });

    // ✅ 채팅방 클릭 이벤트 등록 (JSON 방식으로 수정)
    chatListContainer.addEventListener("click", function (e) {
        const link = e.target.closest(".chat-room-link");
        if (!link) return;
        e.preventDefault();

        const productNo = parseInt(link.getAttribute("data-product-no"));
        const sellerId = parseInt(link.getAttribute("data-seller-id"));

        axios.post("/chat/room", {
            productNo: productNo,
            sellerId: sellerId
        }, {
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                document.querySelector("main").innerHTML = response.data;
            })
            .catch(error => {
                console.error("❌ 채팅방 열기 실패:", error);
                alert("채팅방을 여는 데 실패했습니다.");
            });
    });
});
