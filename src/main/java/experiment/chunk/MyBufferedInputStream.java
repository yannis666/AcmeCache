package experiment.chunk;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class MyBufferedInputStream extends BufferedInputStream {
    private final Chunk chunk;

    public MyBufferedInputStream(InputStream in, Chunk chunk) {
        super(in);
        this.chunk = chunk;
    }
    
    public String readLine() throws IOException {
        chunk.read(this);
        int length = chunk.getLength();
        if (!chunk.atEnd()) {
            // we know we have it all
            String ret = new String(chunk.buf, Chunk.SIZE_BYTES, length);
            // TODO: currently, after each write we add a "next chunk length"
            // TODO: could eliminate this if lead contained whether there was continuation
            chunk.read(this);
            if (chunk.getLength() != 0) {
                throw new IOException();
            }
            return ret;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(chunk.getLength());
        do {
            baos.write(chunk.buf, Chunk.SIZE_BYTES, length);
            chunk.read(this);
            length = chunk.getLength();
        } while (length != 0);
        return baos.toString();
    }
}
