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
            new String(chunk.buf, Chunk.SIZE_BYTES, length);
        }
        //TODO: optimize if only chunk - no need to copy to baos
        ByteArrayOutputStream baos = new ByteArrayOutputStream(chunk.getLength());
        do {
            baos.write(chunk.buf, Chunk.SIZE_BYTES, length);
            chunk.read(this);
            length = chunk.getLength();
        } while (length != 0);
        return baos.toString();
    }
}
