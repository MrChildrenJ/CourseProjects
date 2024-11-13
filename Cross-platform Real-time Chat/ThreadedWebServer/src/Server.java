import java.io.*;
import java.net.*;
import static java.lang.System.*;

public class Server {
    private static final int    PORT = 8080;
    private static final String PATH = "resources_ChatRoom";

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
    public void start() {
        try (ServerSocket listener = new ServerSocket(PORT)) {
            out.println("Server is running on port " + PORT);

            while (true) {
                try {
                    Socket clientConnection = listener.accept();

                    // Create a client handler to handle client, client handler is a runable
                    ClientHandler handler = new ClientHandler(clientConnection);

                    // create a new thread to deal with the client handler (runable)
                    new Thread(handler).start();
                } catch (IOException e) {
                    out.println("Error: Could not handle client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            out.println("Error: Could not start server: " + e.getMessage());
            System.exit(1);
        }
    }

    public static String getPath() {  return PATH;  }
}
