package experiment.chunk;

import java.io.FilterOutputStream;
import java.io.IOException;

/**
 * @author ycosmado
 * @since 1.0
 */
public class MyFilterOutputStream extends FilterOutputStream {
    protected MyFilterOutputStream(MyBufferedOutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        ((MyBufferedOutputStream) out).writeByte((byte) b);
    }

    @Override
    public void close() throws IOException {
        ((MyBufferedOutputStream) out).endChunk();
    }
}
