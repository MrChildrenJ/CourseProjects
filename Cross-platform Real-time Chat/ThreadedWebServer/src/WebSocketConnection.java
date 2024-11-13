import java.util.*;
import java.io.*;
import java.net.Socket;
import static java.lang.System.*;

class WebSocketConnection {
    private final Socket socket;
    private final OutputStream outputStream;
    private String username;
    private ChatRoom currRoom;

    public WebSocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = socket.getOutputStream();
    }

    public void sendMsg(String msg) throws IOException {
        WebSocketFrame frame = WebSocketFrame.createTextFrame(msg);
        WebSocketHandler.writeFrame(outputStream, frame);
    }

    public void setUsername(String username)    {  this.username = username;  }
    public String getUsername()                 {  return this.username;  }
    public void setCurrRoom(ChatRoom currRoom)  {  this.currRoom = currRoom;  }
    public ChatRoom getCurrRoom()               {  return this.currRoom;  }

    public void close() throws IOException      {  socket.close();  }
}
