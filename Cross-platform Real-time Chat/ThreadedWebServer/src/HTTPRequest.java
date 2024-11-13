import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static java.lang.System.out;

class HTTPAnalyzeException extends Exception {
    public HTTPAnalyzeException(String msg)         {  super(msg);  }
}

class HTTPRequest {                                 // To parse requests from HTTP
    private final Socket clientConnection;
    private String method;
    private String path;
    private final Map<String, String> headers;

    public HTTPRequest(Socket clientConnection) {
        this.clientConnection = clientConnection;
        this.headers = new HashMap<>();
    }
    public void parse() throws IOException, HTTPAnalyzeException {
        try {
            Scanner sc = new Scanner(clientConnection.getInputStream());

            if (!sc.hasNextLine())  throw new HTTPAnalyzeException("Empty Request");

            String reqLine = sc.nextLine();
            out.println("Request: " + reqLine);

            String[] reqs = reqLine.split(" ");
            if (reqs.length < 3)    throw new HTTPAnalyzeException("Invalid request format");
            method = reqs[0];
            path = reqs[1];

            // Read remaining headers
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty())    break;

                /* Difference between WebSocket request and http request (example):
                        Upgrade: websocket
                        Connection: Upgrade
                        Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
                        Sec-WebSocket-Version: 13
                        Origin: https://example.com
                 */

                // parse (WebSocket) header
                int colonIndex = line.indexOf(':');
                if (colonIndex > 0) {
                    String key = line.substring(0, colonIndex).trim();
                    String headerValue = line.substring(colonIndex + 1).trim();
                    out.println("Map pair added - " + key + ": " + headerValue);
                    headers.put(key, headerValue);
                }
            }
        } catch (IOException e) {
            out.println("Something wrong in IO" + e.getMessage());
        } catch (HTTPAnalyzeException e) {
            out.println("Something wrong in HTTPAnalyze: " + e.getMessage());
        }
    }

     /* Typical WebSocket request
            GET /chat HTTP/1.1                              // must be GET
            Host: example.com:80                            // all the following five items are indispensable.
            Upgrade: websocket
            Connection: Upgrade
            Sec-WebSocket-Key: zy6Dy9mSAIM7GJZNf9rI1A==     (client sent this key to server)
            Sec-WebSocket-Version: 13
     */

    public boolean isWebSocketRequest() {
        return "GET".equals(this.getMethod()) &&
                this.getHeaders().containsKey("Upgrade") &&
                this.getHeader("Upgrade").equalsIgnoreCase("websocket") &&
                this.getHeaders().containsKey("Connection") &&
                this.getHeader("Connection").equalsIgnoreCase("Upgrade") &&
                this.getHeaders().containsKey("Sec-WebSocket-Key");
    }
    public String getMethod()                       {  return method;  }
    public String getPath()                         {  return path;  }
    public Map<String, String> getHeaders()         {  return headers;  }
    public String getHeader(String key)             {  return headers.get(key);  }
}
