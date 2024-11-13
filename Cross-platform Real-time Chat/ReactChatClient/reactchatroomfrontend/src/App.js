import React, { useEffect, useState, useRef } from "react";
import "./App.css";
import Login from "./Login";
import ChatPage2 from "./ChatPage2";

function App() {
  const [currentPage, setCurrentPage] = useState("login");
  const [userData, setUserData] = useState({ username: "", room: "" });

  const handleJoinRoom = (username, room) => {
    setUserData({ username, room });
    setCurrentPage("chat");
  };

  return (
    <div className="app">
      {currentPage === "login" ? (
        <Login onJoinRoom={handleJoinRoom} />
      ) : (
        <ChatPage2 username={userData.username} room={userData.room} />
      )}
    </div>
  );
}

export default App;
