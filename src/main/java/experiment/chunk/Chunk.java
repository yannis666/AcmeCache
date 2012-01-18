package experiment.chunk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class Chunk {
    public static int SIZE_BYTES = 2;
    public final byte[] buf;
    int pos = SIZE_BYTES;

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
    
    void write(OutputStream out) throws IOException {
        setLengthInternal(pos - SIZE_BYTES);
        out.write(buf, 0, pos);
    }

    void read(InputStream in) throws IOException {
        //TODO: maybe read again...
        if (in.read(buf, 0, SIZE_BYTES) != SIZE_BYTES) {
            throw new IOException();
        }
        int length = getLength();
        if (length == 0) {
            return;
        }

        reset();
        while (length > 0) {
            int read = in.read(buf, pos, length);
            pos += read;
            length -= read;
        }
    }
}
