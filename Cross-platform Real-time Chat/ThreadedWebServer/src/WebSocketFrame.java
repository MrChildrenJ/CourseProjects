import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.System.*;

// WebSocketFrame.java
public class WebSocketFrame {
    private final boolean fin;
    private final int opcode;
    private final byte[] payload;

    public static final int OPCODE_CONTINUATION = 0x00;
    public static final int OPCODE_TEXT = 0x01;             //0000 0001
    public static final int OPCODE_BINARY = 0x02;           //0000 0010
    public static final int OPCODE_CLOSE = 0x08;            //0000 1000
    public static final int OPCODE_PING = 0x09;             //0000 1001
    public static final int OPCODE_PONG = 0x0A;             //0000 1010

    public WebSocketFrame(boolean fin, int opcode, byte[] payload) {
        this.fin = fin;
        this.opcode = opcode;
        this.payload = payload;
    }

    public boolean isFin()      { return fin; }
    public int getOpcode()      { return opcode; }
    public byte[] getPayload()  { return payload; }
    public String getTextPayload() {
        return new String(payload, StandardCharsets.UTF_8);
    }

    public static WebSocketFrame createTextFrame(String text) {
        return new WebSocketFrame(true, OPCODE_TEXT, text.getBytes(StandardCharsets.UTF_8));
    }
}
