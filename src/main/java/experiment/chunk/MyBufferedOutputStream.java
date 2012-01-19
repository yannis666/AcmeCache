package experiment.chunk;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class MyBufferedOutputStream extends BufferedOutputStream {
    private final Chunk chunk;

    public MyBufferedOutputStream(OutputStream outputStream) {
        this(outputStream, new Chunk());
    }

    // for testing
    MyBufferedOutputStream(OutputStream outputStream, Chunk chunk) {
        super(outputStream);
        if (chunk == null) {
            throw new NullPointerException();
        }
        this.chunk = chunk;
    }

    public void writeLine(String line) throws IOException {
        byte[] bytes = line.getBytes();
        writeBytes(bytes, 0, bytes.length);
        flush();
    }

    public void writeObject(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes = baos.toByteArray();
            writeBytes(bytes, 0, bytes.length);
            flush();
        } finally {
            baos.close();
            oos.close();
        }
    }

    private void writeBytes(byte[] bytes, int start, int length) throws IOException {
        chunk.reset();
        for (int i = 0; i < start + length; i++) {
            writeByte(bytes[i]);
        }
        endChunk();
    }

    protected void endChunk() throws IOException {
        writeChunk();
        // TODO: currently, after each write we add a "next chunk length"
        // TODO: could eliminate this if lead contained whether there was continuation
        chunk.reset();
        writeChunk();
    }

    protected void writeByte(byte aByte) throws IOException {
        if (chunk.atEnd()) {
            writeChunk();
            chunk.reset();
        }
        chunk.write(aByte);
    }

    private void writeChunk() throws IOException {
        chunk.close();
        write(chunk.getBytes(), 0, chunk.getPos());
    }

    public OutputStream getOutputStream() {
        return new MyOutputStream(this);
    }

    private static class MyOutputStream extends FilterOutputStream {
        protected MyOutputStream(MyBufferedOutputStream out) {
            super(out);
        }

        @Override
        public void write(int b) throws IOException {
            if (out != null) {
                ((MyBufferedOutputStream) out).writeByte((byte) b);
            } else {
                throw new IOException();
            }
        }

        @Override
        public void close() throws IOException {
            if (out != null) {
                ((MyBufferedOutputStream) out).endChunk();
                out.flush();
                out = null;
            } else {
                throw new IOException();
            }
        }
    }
}
