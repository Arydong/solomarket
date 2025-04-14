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

const currentUserId = document.getElementById("currentUserId")?.value;
const currentNickname = document.getElementById("nickname")?.value;
const chatListContainer = document.getElementById("chat-list");

if (!currentUserId || !chatListContainer) {
    console.error("❌ 사용자 정보 또는 채팅 리스트 컨테이너가 없습니다.");
}

const chatRoomsRef = db.ref("chats");
chatRoomsRef.once("value", (snapshot) => {
    snapshot.forEach((roomSnap) => {
        const chatRoomId = roomSnap.key;
        const messages = roomSnap.val();

        if (!messages) return;

        const messageKeys = Object.keys(messages);
        const lastMessage = messages[messageKeys[messageKeys.length - 1]];

        const isParticipant = Object.values(messages).some(msg =>
            msg.senderId == currentUserId || msg.receiverId == currentUserId
        );
        if (!isParticipant) return;

        const productNo = chatRoomId.split("-")[1];
        const sellerId = lastMessage.senderId;

        const chatItem = document.createElement("div");
        chatItem.classList.add("chat-item");
        chatItem.innerHTML = `
            <a href="/chat/room?productNo=${productNo}&sellerId=${sellerId}" class="chat-room-link">
                <strong>${lastMessage.nickname}</strong><br>
                <span>${lastMessage.message}</span>
            </a>
        `;
        chatListContainer.appendChild(chatItem);
    });
});