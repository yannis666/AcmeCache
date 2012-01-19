package experiment.chunk;

import java.io.IOException;

/**
 * @author ycosmado
 * @since 1.0
 */
public class Chunk {
    public static int SIZE_BYTES = 2;
    private final byte[] buf;
    private int pos = SIZE_BYTES;
    public static final int DEFAULT_SIZE = 4096;

    public Chunk() {
        this(DEFAULT_SIZE);
    }

    public Chunk(int length) {
        if (!checkLength(length, 1, 0xFFFF)) {
            throw new IllegalArgumentException();
        }
        this.buf = new byte[length + SIZE_BYTES];
    }

    public int getLength() throws IOException {
        int length = ((buf[0] & 0xFF) << 8) + (buf[1] & 0xFF);
        if (!checkLength(length, 0, buf.length - SIZE_BYTES)) {
            throw new IOException("invalid length: " + length);
        }
        return length;
    }

    public void setLength(int length) throws IOException {
        if (!checkLength(length, 1, buf.length - SIZE_BYTES)) {
            throw new IOException("invalid length: " + length);
        }
        setLengthInternal(length);
    }

    public void setLengthInternal(int length) {
        buf[0] = (byte)(length >>> 8);
        buf[1] = (byte) length;
    }

    private boolean checkLength(int length, int minLength, int maxLength) {
        return (length >= minLength) && (length <= maxLength);
    }
    
    boolean atEnd() {
        return pos == buf.length;
    }

    void write(int val) {
        buf[pos++] = (byte) val;
    }

    public void close() {
        setLengthInternal(pos - SIZE_BYTES);
    }

    public void reset() {
        pos = SIZE_BYTES;
    }


    public byte[] getBytes() {
        return buf;
    }
    
    public int getPos() {
        return pos;
    }

    public void advancePos(int read) throws IOException {
        if (read < 0 || pos + read > buf.length) {
            throw new IOException();
        }
        pos += read;
    }
}
