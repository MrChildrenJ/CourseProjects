import React, { useEffect, useState } from "react";
import "./Login.css";

const InputWidget = ({ placeholder, value, onChange }) => {
  return (
    <div className="input-widget">
      <input
        type="text"
        placeholder={placeholder}
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
};

const Login = ({ onJoinRoom }) => {
  const [username, setUsername] = useState("");
  const [room, setRoom] = useState("");

  const handleJoinRoom = () => {
    if (username.trim() && room.trim()) onJoinRoom(username, room);
  };

  return (
    <div id="login-page">
      {/* <input type="text" id="user-name" placeholder="User Name" />
      <input type="text" id="room-name" placeholder="Room Name" /> */}
      <InputWidget
        id="user-name"
        placeholder="User Name"
        value={username}
        onChange={setUsername}
      />
      <InputWidget
        id="room-name"
        placeholder="Room Name"
        value={room}
        onChange={setRoom}
      />

      <button type="button" id="join-room" onClick={handleJoinRoom}>
        Join Room
      </button>
    </div>
  );
};

export default Login;
