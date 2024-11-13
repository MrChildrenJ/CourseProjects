import React, { useEffect, useState, useRef } from "react";
import "./ChatPage2.css";

// User class for managing user colors
class User {
  constructor(name) {
    this.name = name;
    this.color = this.getRandomRGB();
  }

  getRandomRGB() {
    const r = Math.floor(Math.random() * 156) + 100;
    const g = Math.floor(Math.random() * 156) + 100;
    const b = Math.floor(Math.random() * 156) + 100;
    return `rgb(${r}, ${g}, ${b})`;
  }
}
const ChatMessage = ({
  message,
  username,
  isSystem,
  isHistory,
  color,
  timestamp,
}) => {
  const getMessageStyle = () => {
    if (isSystem) {
      return { color: message.includes("joined") ? "green" : "gray" };
    }
    return { color: color };
  };

  return (
    <div
      className={`message ${isHistory ? "history-message" : ""}`}
      style={getMessageStyle()}
    >
      {timestamp && `[${new Date(timestamp).toLocaleTimeString()}] `}
      {!isSystem && `${username}: `}
      {message}
    </div>
  );
};

const ChatPage2 = ({ username: initialUsername, room }) => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [socket, setSocket] = useState(null);
  const [users, setUsers] = useState([]);
  const [isReceivingHistory, setIsReceivingHistory] = useState(false);
  const messageEndRef = useRef(null);
  const user = useRef(new User(initialUsername));

  useEffect(() => {
    document.title = `Room - ${room}`;
    const ws = new WebSocket("ws://localhost:8080");

    ws.onopen = () => {
      console.log("Connected to WebSocket");
      ws.send(`join ${initialUsername} ${room}`);
    };

    ws.onerror = (error) => {
      console.error("WebSocket error:", error);
      alert(
        "Failed to connect to chat server. Please check if the server is running."
      );
    };

    ws.onmessage = (event) => {
      console.log("Received message:", event.data);
      try {
        const data = JSON.parse(event.data);

        switch (data.type) {
          case "historyStart":
            setIsReceivingHistory(true);
            setMessages((prev) => [
              ...prev,
              {
                type: "system",
                message: "--- Chat History ---",
                isHistory: true,
              },
            ]);
            break;

          case "historyEnd":
            setIsReceivingHistory(false);
            setMessages((prev) => [
              ...prev,
              {
                type: "system",
                message: "--- Current Messages ---",
                isHistory: true,
              },
            ]);
            break;

          case "message":
            setMessages((prev) => [
              ...prev,
              {
                type: "message",
                username: data.user,
                message: data.message,
                color: data.color,
                timestamp: data.timestamp,
                isHistory: isReceivingHistory,
              },
            ]);
            break;

          case "join":
            setMessages((prev) => [
              ...prev,
              {
                type: "system",
                message: `${data.user} has joined the room`,
                isHistory: isReceivingHistory,
              },
            ]);
            break;

          case "leave":
            setMessages((prev) => [
              ...prev,
              {
                type: "system",
                message: `${data.user} has left the room`,
                isHistory: isReceivingHistory,
              },
            ]);
            break;

          case "userList":
            setUsers(
              data.users.sort((a, b) => a.username.localeCompare(b.username))
            );
            break;
        }
      } catch (error) {
        console.error("Error parsing message:", error);
      }
    };

    setSocket(ws);

    return () => {
      ws.close();
    };
  }, [initialUsername, room]);

  useEffect(() => {
    if (!isReceivingHistory) {
      messageEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages, isReceivingHistory]);

  const sendMessage = () => {
    if (newMessage.trim() && socket) {
      const message = `message ${newMessage.trim()}`;
      socket.send(message);
      setNewMessage("");
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      sendMessage();
    }
  };

  return (
    <div id="chat-container" className="chat-container">
      <div className="chat-header">
        <h2 id="room-header">Room - {room}</h2>
      </div>

      <div className="chat-layout">
        <div id="chat-history" className="messages-area">
          {messages.map((msg, index) => (
            <ChatMessage
              key={index}
              message={msg.message}
              username={msg.username}
              isSystem={msg.type === "system"}
              isHistory={msg.isHistory}
              color={msg.color}
              timestamp={msg.timestamp}
            />
          ))}
          <div ref={messageEndRef} />
        </div>

        <div id="user" className="users-sidebar">
          <h3 className="user-header">Users</h3>
          <ul id="user-list" className="user-list">
            {users.map((user, index) => (
              <li
                key={index}
                style={{ color: user.color }}
                className="user-item"
              >
                {user.username}
              </li>
            ))}
          </ul>
        </div>
      </div>

      <div className="input-container">
        <input
          id="message"
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          onKeyDown={handleKeyPress}
          placeholder="Type your message..."
        />
        <button id="send" onClick={sendMessage}>
          Send
        </button>
      </div>
    </div>
  );
};

export default ChatPage2;
