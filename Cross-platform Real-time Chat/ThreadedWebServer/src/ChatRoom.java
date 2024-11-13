import javax.jms.Message;
import java.io.IOException;
import java.util.*;

import static java.lang.System.*;

class ChatRoom {
    private final String name;
    private final Map<String, User> userList = new HashMap<>();
    private List<ChatMessage> messageHistory = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

    private static class ChatMessage {
        final String type;
        final String username;
        final String message;
        final String color;
        final long timestamp;

        ChatMessage(String type, String username, String message, String color) {
            this.type = type;
            this.username = username;
            this.message = message;
            this.color = color;
            this.timestamp = System.currentTimeMillis();
        }

        String toJSON() {
            return String.format(
                    "{\"type\":\"%s\", \"user\":\"%s\", \"message\":\"%s\", \"color\":\"%s\", \"timestamp\":%d}",
                    type, username, message, color, timestamp);
        }
    }

    public synchronized void addUser(String username, WebSocketConnection connection) {
        User user = new User(username, connection);
        userList.put(username, user);
        sendHistoryToUser(user);
        String joinMsg = createJoinMsg(user);
        broadcastMsg(joinMsg);
        addToHistory("join", username, "join the room", user.getColor());
        broadcastUserList();
    }
    public synchronized void removeUser(String username) {
        userList.remove(username);
        broadcastMsg(createLeaveMsg(username));
        broadcastUserList();
    }

    private void sendHistoryToUser(User user) {
        try {
            user.getConnection().sendMsg("{\"type\":\"historyStart\"}");
            for (ChatMessage message : messageHistory)
                user.getConnection().sendMsg(message.toJSON());
            user.getConnection().sendMsg("{\"type\":\"historyEnd\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addToHistory(String type, String username, String msg, String color) {
        ChatMessage message = new ChatMessage(type, username, msg, color);
        messageHistory.add(message);
    }
    private void broadcastUserList() {
        StringBuilder userListForFrontEnd = new StringBuilder();
        userListForFrontEnd.append("{\"type\":\"userList\", \"users\":[");

        boolean first = true;
        for (User user : userList.values()) {
            if (!first) userListForFrontEnd.append(",");
            userListForFrontEnd.append(String.format("{\"username\":\"%s\", \"color\":\"%s\"}", user.getUsername(), user.getColor()));
            first = false;
        }
        userListForFrontEnd.append("]}");
        broadcastMsg(userListForFrontEnd.toString());
    }

    public synchronized void broadcastMsg(String msg, String username) {
        User sender = userList.get(username);
        if (sender != null) {
            String colorMsg = String.format("{\"type\":\"message\",\"user\":\"%s\",\"room\":\"%s\",\"message\":\"%s\",\"color\":\"%s\"}",
                    username, name, msg, sender.getColor());
            broadcastMsg(colorMsg);
            addToHistory("message", username, msg, sender.getColor());
        }
    }
    public synchronized void broadcastMsg(String message) {
        for (User user : userList.values()) {           // for EVERY user
            try {
                user.getConnection().sendMsg(message);  // send userList
                // "{"type":"userList", "users":[{"username":"%s", "color":"%s"}, {"username":"%s", "color":"%s"}, ... ]}"
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String createJoinMsg(User user) {
        return String.format("{\"type\":\"join\",\"user\":\"%s\",\"room\":\"%s\",\"color\":\"%s\"}", user.getUsername(), this.name, user.getColor());
    }
    private String createLeaveMsg(String username) {
        return String.format("{\"type\":\"leave\",\"user\":\"%s\",\"room\":\"%s\"}", username, this.name);
    }
    public String getRoomName() {  return this.name;  }
}
