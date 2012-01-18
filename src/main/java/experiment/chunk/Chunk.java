package experiment.chunk;

import java.io.IOException;

public class Chunk {
    public static int SIZE_BYTES = 2;
    public final byte[] buf;

    public Chunk(int length) {
        if (!checkLength(length, 0xFFFF)) {
            throw new IllegalArgumentException();
        }
        this.buf = new byte[length + SIZE_BYTES];
    }

    public Chunk(byte[] buf) {
        this.buf = buf;
    }

    public int getLength() throws IOException {
        int length = ((buf[0] & 0xFF) << 8) + (buf[1] & 0xFF);
        if (!checkLength(length)) {
            throw new IOException("invalid length: " + length);
        }
        return length;
    }

    public void setLength(int length) throws IOException {
        if (!checkLength(length)) {
            throw new IOException("invalid length: " + length);
        }
        buf[0] = (byte)(length >>> 8);
        buf[1] = (byte) length;
    }

    private boolean checkLength(int length) {
        return checkLength(length, buf.length - SIZE_BYTES);
    }

    private boolean checkLength(int length, int maxLength) {
        return (length > 0) && (length <= maxLength);
    }

}
