package experiment.chunk;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class MyBufferedInputStream extends BufferedInputStream {
    private final Chunk chunk;

    public MyBufferedInputStream(InputStream in) {
        this(in, new Chunk());
    }

    // for testing
    MyBufferedInputStream(InputStream in, Chunk chunk) {
        super(in);
        this.chunk = chunk;
    }
    
    public String readLine() throws IOException {
        int length = readChunk();
        if (!chunk.atEnd()) {
            // we know we have it all
            String ret = new String(chunk.getBytes(), Chunk.SIZE_BYTES, length);
            // TODO: currently, after each write we add a "next chunk length"
            // TODO: could eliminate this if lead contained whether there was continuation
            if (readChunk() != 0) {
                throw new IOException();
            }
            return ret;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2 * length);
        do {
            baos.write(chunk.getBytes(), Chunk.SIZE_BYTES, length);
            length = readChunk();
        } while (length != 0);
        try {
            return baos.toString();
        } finally {
            baos.close();
        }
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        int length = readChunk();
        if (!chunk.atEnd()) {
            // we know we have it all
            ByteArrayInputStream bais = new ByteArrayInputStream(chunk.getBytes(), Chunk.SIZE_BYTES, length);
            ObjectInputStream ois = new ObjectInputStream(bais);
            try {
                Object ret = ois.readObject();
                // TODO: currently, after each write we add a "next chunk length"
                // TODO: could eliminate this if lead contained whether there was continuation
                if (readChunk() != 0) {
                    throw new IOException();
                }
                return ret;
            } finally {
                ois.close();
                bais.close();
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2 * length);
        do {
            baos.write(chunk.getBytes(), Chunk.SIZE_BYTES, length);
            length = readChunk();
        } while (length != 0);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            return ois.readObject();
        } finally {
            baos.close();
            bais.close();
            ois.close();
        }
    }

    private int readChunk() throws IOException {
        //TODO: maybe read again...
        byte[] chunkBytes = chunk.getBytes();
        if (read(chunkBytes, 0, Chunk.SIZE_BYTES) != Chunk.SIZE_BYTES) {
            throw new IOException();
        }
        final int chunkLength = chunk.getLength();
        if (chunkLength != 0) {
            chunk.reset();
            int length = chunkLength;
            do {
                int read = read(chunkBytes, chunk.getPos(), length);
                chunk.advancePos(read);
                length -= read;
            }  while (length > 0);
        }
        return chunkLength;
    }
}
