import java.io.*;
import java.net.Socket;
import static java.lang.System.*;

class ClientHandler implements Runnable {
    private final Socket clientConnection;

    public ClientHandler(Socket clientConnection)   {  this.clientConnection = clientConnection;  }
    @Override
    public void run() {
        out.println("Connection established in thread: " + Thread.currentThread().threadId());
        try {
            HTTPRequest request = new HTTPRequest(clientConnection);
            HTTPResponse response = new HTTPResponse(clientConnection);

            try {
                request.parse();
                if (request.isWebSocketRequest())               handleWebSocket(request);               // if ws -> handleWS
                else if (request.getMethod().equals("GET"))     handleRequest(request, response);       // if http -> handleHTTP
                else                                            response.sendError(405, "Method Not Allowed");
            } catch (IOException e) {
                System.out.println("I/O error while handling client: " + e.getMessage());
                response.sendError(500, "Internal Server Error");
            } catch (HTTPAnalyzeException e) {
                System.out.println("Error analyzing request: " + e.getMessage());
                response.sendError(400, "Bad Request");
            }
        } catch (Exception e) {
            out.println("Unexpected error: " + e.getMessage());
        } finally {
            try {
                clientConnection.close();
            } catch (IOException e) {
                out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }

    private void handleWebSocket(HTTPRequest request) {
        try {
            WebSocketConnection connection = new WebSocketConnection(clientConnection);     // build a WSConnection

            // WebSocket handshake
            WebSocketHandler.handleHandshake(clientConnection.getOutputStream(), request);

            // Chat server loop
            while (!clientConnection.isClosed()) {
                WebSocketFrame frame = WebSocketHandler.readFrame(clientConnection.getInputStream());

                // Handle close frame
                if (frame.getOpcode() == WebSocketFrame.OPCODE_CLOSE) {
                    handleClose(connection);
                    break;
                }

                // handle message sent from client
                if (frame.getOpcode() == WebSocketFrame.OPCODE_TEXT) {
                    handleMessage(connection, frame.getTextPayload());
                }
                //WebSocketHandler.writeFrame(clientConnection.getOutputStream(), frame);
            }
        } catch (IOException e) {
            System.out.println("WebSocket error: " + e.getMessage());
            try {
                clientConnection.close();
            } catch (IOException closeError) {
                System.out.println("Error closing connection: " + closeError.getMessage());
            }
        }
    }
    private void handleRequest(HTTPRequest request, HTTPResponse response) throws IOException {
        String reqPath = request.getPath();
        if (reqPath.equals("/")) {
            reqPath = "/index.html";
        }

        File file = new File(Server.getPath() + reqPath);
        out.println("Looking for file at: " + file.getAbsolutePath());

        if (file.exists() && file.isFile()) {
            response.sendFile(file);
            //sendFileSlowly(file, response);
        } else {
            response.sendError(404, "Not Found");
        }
    }

    private void handleMessage(WebSocketConnection connection, String textPayload) {
        out.println("Received raw message: " + textPayload);

        String[] parts = textPayload.split(" ", 2);
        if (parts.length < 2) {
            out.println("Invalid msg format");
            return;
        }
        // ex: join JJ room1
        String command = parts[0];          // join, leave, message
        String content = parts[1];          // if join -> "username + roomName"

        if (command.equals("join"))             handleJoinCommand(connection, content);
        else if (command.equals("message"))     handleChatMessage(connection, content);
        else                                    out.println("Unknown command: " + command);
    }
    private void handleJoinCommand(WebSocketConnection connection, String content) {
        String[] parts = content.split(" ", 2);
        if (parts.length != 2) return;

        String username = parts[0];
        String roomName = parts[1];

        connection.setUsername(username);
        ChatRoom room = ChatServer.getRoom(roomName);
        connection.setCurrRoom(room);
        room.addUser(username, connection);
    }
    private void handleChatMessage(WebSocketConnection connection, String content) {
        connection.getCurrRoom().broadcastMsg(content, connection.getUsername());
    }

    private void handleClose(WebSocketConnection connection) throws IOException {
        if (connection.getCurrRoom() != null && connection.getUsername() != null) {
            connection.getCurrRoom().removeUser(connection.getUsername());
        }
        connection.close();
    }
    private void sendFileSlowly(File file, HTTPResponse response) throws IOException {
        // Send headers first
        PrintWriter pw = new PrintWriter(clientConnection.getOutputStream(), true);
        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-Type: " + response.getContentType(file));
        pw.println("Content-Length: " + file.length());
        pw.println();
        pw.flush();
        out.println("Headers sent");

        // Send the file content slowly
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            OutputStream socketOut = clientConnection.getOutputStream();
            int fileSize = (int) file.length();

            for (int i = 0; i < fileSize; i++) {
                socketOut.write(fileInputStream.read());
                socketOut.flush();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            out.println("Slowly sending file");
        }
    }
}