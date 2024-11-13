import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import static java.lang.System.*;

class HTTPResponse {
    private final Socket clientConnection;

    HTTPResponse(Socket clientConnection) {  this.clientConnection = clientConnection;  }

    public void sendError(int statusCode, String statusMsg) throws IOException {
        try (PrintWriter pw = new PrintWriter(clientConnection.getOutputStream(), true)) {
            pw.println("HTTP/1.1 " + statusCode + " " + statusMsg);
            pw.println("Content-Type: text/html");
            pw.println();
            pw.println("<html><body><h1>" + statusCode + " " + statusMsg + "</h1></body></html>");
            pw.flush();

            clientConnection.getOutputStream().flush();
        }
    }
    public void sendFile(File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(clientConnection.getOutputStream(), true)) {
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: " + getContentType(file));
            pw.println("Content-Length: " + file.length());
            pw.println();
            pw.flush();
            out.println("Headers sent");

            FileInputStream input = new FileInputStream(file);
            input.transferTo(clientConnection.getOutputStream());
            input.close();
            out.println("File content sent");

            clientConnection.getOutputStream().flush();
            out.println("Response flushed");
        }
    }
    public String getContentType(File file) {
        String fileName = file.getName();
        if (fileName.endsWith(".html") || fileName.endsWith(".htm"))    return "text/html";
        if (fileName.endsWith(".css"))                                  return "text/css";
        if (fileName.endsWith(".js"))                                   return "application/javascript";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))    return "image/jpeg";
        if (fileName.endsWith(".png"))                                  return "image/png";
        if (fileName.endsWith(".gif"))                                  return "image/gif";
        return "application/octet-stream";
    }
}