const ws = new WebSocket("ws://localhost:8080");
//const ws = new WebSocket("ws://" + window.location.host);
const output = document.getElementById("output");
let messages = document.querySelector("#messages");
const send = document.querySelector("#messageInput");

ws.onopen = () => {
  console.log("Connected to WebSocket");
  addMessage("Connected to server");
};

ws.onmessage = (event) => {
  console.log("Received:", event.data);
  addMessage("Received: " + event.data);
};

ws.onclose = () => {
  console.log("Disconnected from WebSocket");
  addMessage("Disconnected from server");
};

ws.onerror = (error) => {
  console.error("WebSocket error:", error);
  addMessage("Error: " + error);
};

function sendMessage() {
  const input = document.getElementById("messageInput");
  const message = input.value;
  //ws.send(message);
  addMessage("Sent: " + message);
  input.value = "";
}

function addMessage(message) {
  const div = document.createElement("div");
  div.textContent = message;
  messages.appendChild(div);
}

send.addEventListener("click", sendMessage);
