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
        const chatRoomIdFinal = `product-${productNo}-buyer-${Math.min(currentUserId, sellerId)}-seller-${Math.max(currentUserId, sellerId)}`;

        const chatItem = document.createElement("div");
        chatItem.classList.add("chat-item");
        chatItem.innerHTML = `
  <a href="/chat/room?productNo=${productNo}&sellerId=${sellerId}&chatRoomId=${chatRoomId}" 
     class="chat-room-link" 
     style="
        display: flex;
        flex-direction: column;
        gap: 6px;
        padding: 16px;
        background-color: #f9f9f9;
        border-radius: 12px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
        text-decoration: none;
        color: #333;
        transition: background-color 0.2s;
     "
     onmouseover="this.style.backgroundColor='#f0f0f0'"
     onmouseout="this.style.backgroundColor='#f9f9f9'">
     
     <div style="font-weight: bold; font-size: 18px;">${lastMessage.nickname}</div>
     <div style="font-size: 14px; color: #666;">${lastMessage.message}</div>
  </a>
`;
        chatListContainer.appendChild(chatItem);
    });
});