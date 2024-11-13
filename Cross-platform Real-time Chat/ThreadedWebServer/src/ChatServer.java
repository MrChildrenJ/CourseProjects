import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static java.lang.System.*;

class ChatServer {
    private static final ConcurrentHashMap<String, ChatRoom> roomList = new ConcurrentHashMap<>();

    public static ChatRoom getRoom(String roomName) {
        return roomList.computeIfAbsent(roomName, ChatRoom::new);
    }

    public void removeRoom(String roomName) {  roomList.remove(roomName);  }
}
