let userName = document.querySelector("#user-name");
let roomName = document.querySelector("#room-name");
let join = document.querySelector("#join-room");
let chatContainer = document.querySelector("#chat-container");
let loginContainer = document.querySelector("#login-container");

let ws = null;

function connectWebSocket() {
  ws = new WebSocket("ws://localhost:8080");
  ws.onopen = function () {
    console.log("WebSocket connection established");
  };
  ws.onerror = function (error) {
    console.error("WebSocket error:", error);
    alert(
      "Failed to connect to chat server. Please check if the server is running."
    );
  };
  ws.onclose = function () {
    console.log("WebSocket connection closed");
  };
  ws.onmessage = function (event) {
    console.log("Received message:", event.data);
    try {
      const data = JSON.parse(event.data);

      switch (data.type) {
        case "message":
          const messageDiv = document.createElement("div");
          messageDiv.textContent = `[${data.room}] ${data.user}: ${data.message}`;
          messageDiv.style.color = data.color;
          chatHistory.appendChild(messageDiv);
          chatHistory.scrollTop = chatHistory.scrollHeight;
          break;

        case "join":
          const joinMsg = document.createElement("div");
          joinMsg.textContent = `${data.user} has joined ${data.room}.`;
          joinMsg.style.color = "green";
          chatHistory.appendChild(joinMsg);
          chatHistory.scrollTop = chatHistory.scrollHeight;
          break;

        case "leave":
          const leaveMsg = document.createElement("div");
          leaveMsg.textContent = `${data.user} has left ${data.room}.`;
          leaveMsg.style.color = "gray";
          chatHistory.appendChild(leaveMsg);
          chatHistory.scrollTop = chatHistory.scrollHeight;
          break;

        case "userList":
          updateUserList(data.users); // server will send users array
          break;

        case "error":
          alert(data.message);
          break;
      }
    } catch (e) {
      console.error("Error parsing message:", e);
    }
  };
}

let send = document.querySelector("#send");
let message = document.querySelector("#message");
let chatHistory = document.querySelector("#chat-history");

class User {
  constructor(name) {
    this.name = name;
    this.color = this.getRandomRGB();
  }

  getRandomRGB() {
    const r = Math.floor(Math.random() * 156) + 100; // 100-255 for better readability
    const g = Math.floor(Math.random() * 156) + 100;
    const b = Math.floor(Math.random() * 156) + 100;
    return `rgb(${r}, ${g}, ${b})`;
  }
}

let users = [];

join.addEventListener("click", (e) => {
  e.preventDefault();
  if (!ws || ws.readyState !== WebSocket.OPEN) {
    connectWebSocket();
    ws.onopen = function () {
      handleJoin();
    };
  } else {
    handleJoin();
  }
});

function updateUserList() {
  const userList = document.querySelector("#user-list");
  userList.innerHTML = "";
}

function handleJoin() {
  if (!checkingIdAndRoom(roomName, userName)) return;

  console.log("Sending join message...");
  const msg = "join " + userName.value + " " + roomName.value;
  ws.send(msg);
  console.log("Join message sent:", msg);

  const newUser = new User(userName.value);
  users.push(newUser);
  updateUserList();

  const roomHeader = document.querySelector("#room-header");
  roomHeader.textContent = "Room - " + roomName.value;

  // Show chat container and hide login
  loginContainer.style.display = "none";
  chatContainer.style.display = "block";
  message.focus();
}

function checkingIdAndRoom(roomName, userName) {
  const username = userName.value.trim();
  const roomname = roomName.value.trim();

  if (!username || !roomname) {
    alert("Username and room name can't be blank");
    return false;
  }

  if (!/^[a-zA-Z0-9_-]+$/.test(username)) {
    alert("Username can only contain letters, numbers, underscore and hyphen");
    return false;
  }

  if (!/^[a-z][a-z0-9]*$/.test(roomname)) {
    alert(
      "Room name must start with lowercase letter and can contain only lowercase letters or numbers"
    );
    return false;
  }

  return true;
}

send.addEventListener("click", () => {
  sendMessage();
});

message.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    event.preventDefault();
    sendMessage();
  }
});

function sendMessage() {
  const messageText = message.value.trim();
  if (!messageText) return;

  if (ws && ws.readyState === WebSocket.OPEN) {
    console.log("Sending message:", messageText);
    const msg = `message ${messageText}`;
    ws.send(msg);
    message.value = "";
  } else {
    alert("Not connected to chat server!");
  }
}

// Initial WebSocket connection
connectWebSocket();
