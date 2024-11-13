// WebSocketHandler.java
import java.io.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class WebSocketHandler {
    private static final String SPEC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    /* Typical server response for WebSocket request
            HTTP/1.1 101 Switching Protocols                // 101 -> accept WS connection upgrade
            Upgrade: websocket
            Connection: Upgrade
            Sec-WebSocket-Accept: dGhlIHNhbXBsZSBub25jZQ==  // when we see this line in header, we know we established ws connection
     */

    public static void handleHandshake(OutputStream output, HTTPRequest request) throws IOException {
        String key = request.getHeader("Sec-WebSocket-Key");
        if (key == null)    throw new IOException("Missing Sec-WebSocket-Key header");

        String acceptKey = generateAcceptKey(key);

        PrintWriter writer = new PrintWriter(output, true);
        writer.println("HTTP/1.1 101 Switching Protocols");
        writer.println("Upgrade: websocket");
        writer.println("Connection: Upgrade");
        writer.println("Sec-WebSocket-Accept: " + acceptKey);
        writer.println();
        writer.flush();
    }
    private static String generateAcceptKey(String key) {
        try {
            String concatenated = key + SPEC_GUID;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(concatenated.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate accept key", e);
        }
    }

    public static WebSocketFrame readFrame(InputStream inputStream) throws IOException {
        DataInputStream input = new DataInputStream(inputStream);

        // Read first byte
        int firstByte = input.read();                                           // InputStream only read ONE byte at a time
        if (firstByte == -1)    throw new IOException("End of stream reached"); // no more data can be read

        boolean fin = (firstByte & 0x80) != 0;      // (1stByte & 1000 0000) -> 1st bit of 1stByte
        int opcode = firstByte & 0x0F;              // (1stByte & 0000 1111) -> 5-8 bit of 1stByte

        // Read second byte
        int secondByte = input.read();
        if (secondByte == -1)   throw new IOException("Unexpected end of stream");

        boolean masked = (secondByte & 0x80) != 0;  // (2ndByte & 1000 0000) -> 1st bit of 2ndByte
        long payloadLength = secondByte & 0x7F;     // (2ndByte & 0111 1111) -> 2-8 bit of 2ndByte, max = 127

        // Handle extended payload length
        if (payloadLength == 126)       payloadLength = input.readShort() & 0xffff;    // read byte 3-4
        else if (payloadLength == 127)  payloadLength = input.readLong();  // read byte 3-10

        // Validate payload length
        if (payloadLength < 0 || payloadLength > Integer.MAX_VALUE) throw new IOException("Invalid payload length: " + payloadLength);

        // Read masking key if present
        byte[] maskingKey = null;
        if (masked) {
            maskingKey = new byte[4];
            input.readFully(maskingKey);   // to make sure that we read 4 bytes
        }

        // Read payload
        byte[] payload = new byte[(int)payloadLength];
        input.readFully(payload);          // to make sure that we read "a length of payload" of bytes

        // Unmask if necessary
        if (masked) {
            for (int i = 0; i < payload.length; i++) {
                payload[i] = (byte)(payload[i] ^ maskingKey[i % 4]);
            }
        }
        return new WebSocketFrame(fin, opcode, payload);
    }
    public static void writeFrame(OutputStream output, WebSocketFrame frame) throws IOException {
        // 1st byte: fin and opcode
        output.write((frame.isFin() ? 0x80 : 0x00) | frame.getOpcode());    // (isFin ? 10000000 : 00000000) | opcode

        // 2nd byte: mask and payload length
        byte[] payload = frame.getPayload();
        if (payload.length <= 125)      output.write(payload.length);
        else if (payload.length <= 65535) { // payload length <= 2^16 - 1 (write 2 bytes)
            // place 126 (01111110) in first 7 bits of the length section to indicate usage of the following 2bytes for length
            output.write(126);

            // 16bit >> 8 => 8bit, get "left 8", (left 8) | 1111 1111, then write
            output.write((payload.length >> 8) & 0xFF);

            // 16bit | 1111 1111, get "right 8", then write
            output.write(payload.length & 0xFF);
        } else {
            output.write(127);                  // same as previous scenario
            for (int i = 7; i >= 0; i--)        // write 8 bytes
                // from most left to most right
                // 1st iteration: length(64 bit) >> 8 * 7  => get left most 8 bit (L1), (L1 | ff) => L1, then write, and so on
                output.write((int)((payload.length >> (8 * i)) & 0xFF));
        }
        // Write payload
        output.write(payload);
        output.flush();
    }

    private static int readTwoBytes(InputStream input) throws IOException {
        int b1 = input.read();      // read a byte(8 bit), but put it in a int (32 bit) -> 0x0000 00ab
        int b2 = input.read();      // same, 0x0000 00cd
        if (b1 == -1 || b2 == -1)   throw new IOException("Unexpected end of stream");

        // b1 are b2 are still 8 bits
        return ((b1 & 0xFF) << 8) | (b2 & 0xFF);
        // left: b1 & 0xff = 00ab, 00ab << 8 = ab00
        // right: b2 & 0xff = 00cd
        // left | right = 0xabcd (16 bit, short, max 65535)

    }
    private static long readEightBytes(InputStream input) throws IOException {
        long length = 0;                // 64bit, 0x1234 5678 90ab cdef
        for (int i = 0; i < 8; i++) {   // read one byte 8 times
            int b = input.read();       // b1, b2, b3, ... , b8
            if (b == -1)    throw new IOException("Unexpected end of stream");
            length = (length << 8) | (b & 0xFF);
            // 1st iteration: if b = 1, length = 0 | 1 = 1
            // 2nd iteration; if b = 2, length = 1 << 8 | 2 = 0100 | 0010 = 0110 = 6
            // length = 0xb1b2 b3b4 b5b6 b7b8 (8-byte, 64 bit)
        }
        return length;
    }
    private static void readFully(InputStream input, byte[] buffer) throws IOException {
        int totalRead = 0;
        while (totalRead < buffer.length) {
            int read = input.read(buffer, totalRead, buffer.length - totalRead);
            if (read == -1)     throw new IOException("Unexpected end of stream");
            totalRead += read;
        }
    }
}

