// ChatScreen.js
import React, { useState, useEffect, useRef } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  FlatList,
  StyleSheet,
  KeyboardAvoidingView,
  Platform,
  Alert,
} from "react-native";

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

export default function ChatScreen({ route }) {
  const { username, roomName } = route.params;
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [users, setUsers] = useState([]);
  const [isReceivingHistory, setIsReceivingHistory] = useState(false);
  const wsRef = useRef(null);
  const user = useRef(new User(username));

  useEffect(() => {
    const serverUrl = Platform.select({
      ios: "ws://10.18.179.228:8080",
      android: "ws://10.18.179.228:8080",
    });

    wsRef.current = new WebSocket(serverUrl);

    wsRef.current.onopen = () => {
      console.log("Successfully connected to the server");
      const joinMessage = `join ${username} ${roomName}`;
      wsRef.current.send(joinMessage);
    };

    wsRef.current.onmessage = (event) => {
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
                text: data.message,
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
                text: `${data.user} has joined the room`,
                isHistory: isReceivingHistory,
              },
            ]);
            break;

          case "leave":
            setMessages((prev) => [
              ...prev,
              {
                type: "system",
                text: `${data.user} has left the room`,
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

    wsRef.current.onerror = (error) => {
      console.error("WebSocket error:", error);
      Alert.alert("Error", "Failed to connect to chat server");
    };

    wsRef.current.onclose = () => {
      console.log("Disconnected from the server");
    };

    return () => {
      if (wsRef.current) {
        wsRef.current.close();
      }
    };
  }, [username, roomName]);

  const sendMessage = () => {
    if (newMessage.trim() && wsRef.current?.readyState === WebSocket.OPEN) {
      const messageToSend = `message ${newMessage.trim()}`;
      wsRef.current.send(messageToSend);
      setNewMessage("");
    }
  };

  const renderMessage = ({ item }) => (
    <View
      style={[
        styles.messageContainer,
        item.type === "system"
          ? styles.systemMessage
          : item.username === username
          ? styles.ownMessage
          : styles.otherMessage,
      ]}
    >
      {item.type !== "system" && (
        <Text style={[styles.username, { color: item.color }]}>
          {item.username}
        </Text>
      )}
      <Text
        style={[
          styles.messageText,
          item.type === "system" && {
            color: item.text?.includes("joined") ? "green" : "gray",
            fontStyle: "italic",
          },
        ]}
      >
        {item.text || item.message}
      </Text>
      {item.timestamp && (
        <Text style={styles.timestamp}>
          {new Date(item.timestamp).toLocaleTimeString()}
        </Text>
      )}
    </View>
  );

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      keyboardVerticalOffset={90}
    >
      <FlatList
        data={messages}
        renderItem={renderMessage}
        keyExtractor={(item, index) => index.toString()}
        style={styles.messagesList}
      />
      <View style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          value={newMessage}
          onChangeText={setNewMessage}
          placeholder="Type a message..."
          multiline
          onSubmitEditing={sendMessage}
        />
        <TouchableOpacity style={styles.sendButton} onPress={sendMessage}>
          <Text style={styles.sendButtonText}>Send</Text>
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f5f5f5",
  },
  messagesList: {
    flex: 1,
    padding: 10,
  },
  messageContainer: {
    backgroundColor: "white",
    padding: 10,
    marginBottom: 10,
    borderRadius: 5,
    borderWidth: 1,
    borderColor: "#ddd",
    maxWidth: "80%",
  },
  ownMessage: {
    alignSelf: "flex-end",
    backgroundColor: "#DCF8C6",
  },
  otherMessage: {
    alignSelf: "flex-start",
    backgroundColor: "white",
  },
  username: {
    fontWeight: "bold",
    marginBottom: 5,
  },
  messageText: {
    fontSize: 16,
  },
  timestamp: {
    fontSize: 12,
    color: "#666",
    marginTop: 5,
  },
  inputContainer: {
    flexDirection: "row",
    padding: 10,
    backgroundColor: "white",
    borderTopWidth: 1,
    borderTopColor: "#ddd",
  },
  input: {
    flex: 1,
    backgroundColor: "#f8f8f8",
    padding: 10,
    borderRadius: 20,
    marginRight: 10,
    maxHeight: 100,
  },
  sendButton: {
    backgroundColor: "#007AFF",
    padding: 10,
    borderRadius: 20,
    justifyContent: "center",
  },
  sendButtonText: {
    color: "white",
    fontWeight: "bold",
  },
});
