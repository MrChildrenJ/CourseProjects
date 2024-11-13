import React, { useEffect, useState, useRef } from "react";
import "./ChatPage.css";

class User {
  constructor(name) {
    this.name = name;
    this.color = this.getRandomRGB();
  }

  getRandomRGB() {
    const r = Math.floor(Math.random() * 128) + 100;
    const g = Math.floor(Math.random() * 128) + 100;
    const b = Math.floor(Math.random() * 128) + 100;
    return `rgb(${r}, ${g}, ${b})`;
  }
}

const ChatMessage = ({ message, username, isOwnMessage }) => {
  return (
    <div
      className={`message ${isOwnMessage ? "own-message" : "other-message"}`}
    >
      <div className="message-content">
        {!isOwnMessage && <div className="message-username">{username}</div>}
        <div className="message-text">{message}</div>
      </div>
    </div>
  );
};

const ChatPage = ({ username, room }) => {
  const [msg, setMsg] = useState([]);
  const [newMsg, setNewMsg] = useState("");
  const [socket, setSocket] = useState(null);
  const msgEndRef = useRef(null);

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:3000");

    ws.onopen = () => {
      console.log("Connected to WebSocket");
      ws.send(
        JSON.stringify({
          type: "join",
          username,
          room,
        })
      );
    };

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      if (data.type === "message") {
        setMsg((prev) => [
          ...prev,
          {
            username: data.username,
            message: data.message,
            timestamp: new Date().getTime(),
          },
        ]);
      }
    };

    ws.onclose = () => {
      console.log("Disconnected from WebSocket");
    };

    setSocket(ws);

    return () => {
      ws.close();
    };
  }, [username, room]);

  const sendMessage = () => {
    if (newMsg.trim() && socket) {
      const msgData = {
        type: "message",
        username,
        room,
        message: newMsg,
      };
      socket.send(JSON.stringify(msgData));
      setNewMsg("");
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  useEffect(() => {
    msgEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [msg]);

  return (
    <div id="chat-container" className="chat-container">
      <div className="chat-header">
        <h2 id="room-header">Room - {room}</h2>
      </div>

      <div className="chat-layout">
        <div className="messages-area">
          {msg.map((msg, index) => (
            <ChatMessage
              key={index}
              message={msg.message}
              username={msg.username}
              isOwnMessage={msg.username === username}
            />
          ))}
          <div ref={msgEndRef} />
        </div>

        <div id="user" className="users-sidebar">
          <h3 className="user-header">Users</h3>
          <ul id="user-list" className="user-list"></ul>
        </div>
      </div>

      <div className="input-container">
        <input
          type="text"
          value={newMsg}
          onChange={(e) => setNewMsg(e.target.value)}
          onKeyDown={handleKeyPress}
          placeholder="Type your message..."
        />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>

    // <div className="chat-page">
    //   <div className="chat-container">
    //     <div className="chat-header">
    //       <h2>Room - {room}</h2>
    //       <p>Logged in as {username}</p>
    //     </div>

    //     <div className="chat-layout">
    //       {msg.map((msg, index) => (
    //         <ChatMessage
    //           key={index}
    //           message={msg.message}
    //           username={msg.username}
    //           isOwnMessage={msg.username === username}
    //         />
    //       ))}
    //       <div ref={msgEndRef} />
    //     </div>

    //     <div className="message-input">
    //       <input
    //         type="text"
    //         value={newMsg}
    //         onChange={(e) => setNewMsg(e.target.value)}
    //         onKeyDown={handleKeyPress}
    //         placeholder="Type your message..."
    //       />
    //       <button onClick={sendMessage}>Send</button>
    //     </div>
    //   </div>
    // </div>
  );
};

export default ChatPage;
