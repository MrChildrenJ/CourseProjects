import java.util.*;
import static java.lang.System.*;

class User {
    private final String username;
    private final WebSocketConnection conn;
    private final String color;

    public User(String username, WebSocketConnection conn) {
        this.username = username;
        this.conn = conn;
        this.color = generateRandomColor();
    }

    private String generateRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return String.format("rgb(%d,%d,%d)", r, g, b);
    }
    public String getUsername()                 {  return username;  }
    public WebSocketConnection getConnection()  {  return conn;  }
    public String getColor()                    {  return color;  }
}
